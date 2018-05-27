package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.billdetail.BillDetail;
import com.ecjtu.erp.model.purchase.InOrder;
import com.ecjtu.erp.model.purchase.PayOrder;
import com.ecjtu.erp.model.purchase.PurchaseOrder;
import com.ecjtu.erp.model.supplier.SupplierGood;
import com.ecjtu.erp.repository.*;
import com.ecjtu.erp.repository.specification.InOrderSpecification;
import com.ecjtu.erp.service.InOrderService;
import com.ecjtu.erp.util.JsonResult;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 16:12 2018/5/14
 * @Modified By:
 */
@Service
public class InOrderServiceImpl extends BaseServiceImpl implements InOrderService {
    private static final Logger log = LoggerFactory.getLogger(InOrderServiceImpl.class);

    @Autowired
    private InOrderRepository inOrderRepository;
    @Autowired
    private PayOrderRepository payOrderRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private SupplierGoodRepository supplierGoodRepository;
    @Autowired
    private BillDetailRepository billDetailRepository;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public List<InOrder> search(InOrder inOrder) {
        return inOrderRepository.findAll(new InOrderSpecification(inOrder));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveInOrder(InOrder inOrder) {
        if(Objects.isNull(inOrder)){
            return new JsonResult(500);
        }
        try {
            if(inOrder.getNumber() < inOrder.getInNumber()){
                return new JsonResult(401, "入库数量不能大于到货数量");
            }
            if(Objects.isNull(inOrder.getInOrderId())){
                log.info("新增:{}", inOrder);
                inOrder.setStatus(InOrder.DEFAULT);
                inOrder.setInTime(LocalDateTime.now());
                inOrderRepository.save(inOrder);
                PayOrder payOrder = payOrderRepository.findById(inOrder.getPayOrderId()).get();
                payOrder.setStatus(PayOrder.BINDED);
                payOrderRepository.save(payOrder);
            }else {
                log.info("修改:{}", inOrder);
                InOrder old = inOrderRepository.findById(inOrder.getInOrderId()).get();
                if(!Objects.isNull(old)){
                    old.setNumber(inOrder.getNumber());
                    old.setDescription(inOrder.getDescription());
                    old.setInNumber(inOrder.getInNumber());
                    old.setqT(inOrder.getqT());
                    old.setPayOrderId(inOrder.getPayOrderId());
                    inOrderRepository.save(old);
                    PayOrder payOrder1 = payOrderRepository.findById(old.getPayOrderId()).get();
                    payOrder1.setStatus(PayOrder.NO_FINISH);
                    payOrderRepository.save(payOrder1);
                    PayOrder payOrder2 = payOrderRepository.findById(inOrder.getPayOrderId()).get();
                    payOrder2.setStatus(PayOrder.BINDED);
                    payOrderRepository.save(payOrder2);
                }
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存入库单失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult setInOrderStatus(Long inOrderId, Integer flag) {
        if(Objects.isNull(inOrderId) || Objects.isNull(flag)){
            return new JsonResult(400);
        }
        try {
            InOrder inOrder = inOrderRepository.findById(inOrderId).get();
            if(InOrder.ACCESS.equals(flag)){
                inOrder.setStatus(InOrder.ACCESS);
                inOrder.setFinishTime(LocalDateTime.now());
                inOrder.setFinishPurchase(LocalDateTime.now());
                inOrderRepository.save(inOrder);
                //todo 重新计算库存，重新修改付款单，重新修改采购单
                taskExecutor.execute(() -> afterAccess(inOrder));
            }else if(InOrder.NO_ACCESS.equals(flag)){
                inOrder.setStatus(InOrder.NO_ACCESS);
                inOrderRepository.save(inOrder);
            }else if(InOrder.CANCEL.equals(flag)){
                inOrder.setStatus(InOrder.CANCEL);
                inOrderRepository.save(inOrder);
                //todo 取消付款单 采购失败采购单
                taskExecutor.execute(() -> afterCancel(inOrder));
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("设置入库单状态失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    /**
     * 审核通之后的操作
     * @param inOrder
     */
    private void afterAccess(InOrder inOrder){
        PayOrder payOrder = payOrderRepository.findById(inOrder.getPayOrderId()).get();
        PurchaseOrder purchaseOrder = purchaseRepository.findById(payOrder.getPurchaseOrderId()).get();
        SupplierGood good = supplierGoodRepository.findById(purchaseOrder.getSupplierGoodId()).get();
        payOrder.setStatus(PayOrder.FINISH);
        payOrder.setPayed(new BigDecimal(good.getBuy()).multiply(new BigDecimal(inOrder.getInNumber())).setScale(2).doubleValue());
        purchaseOrder.setStatus(PurchaseOrder.FINISH);
        purchaseOrder.setInOrderId(inOrder.getInOrderId());
        good.setCurrentInventory(good.getCurrentInventory() + inOrder.getInNumber());
        payOrderRepository.save(payOrder);
        purchaseRepository.save(purchaseOrder);
        supplierGoodRepository.save(good);


        //记录收支明细
        BillDetail billDetail = new BillDetail();
        Pageable pageable = new PageRequest(0, 1, new Sort(new Sort.Order(Sort.Direction.DESC, "occurTime")));
        Page<BillDetail> page = billDetailRepository.findAll(pageable);
        if(CollectionUtils.isNotEmpty(page.getContent())){
            billDetail.setOccurTime(new Date());
            billDetail.setPayOrderId(payOrder.getPayOrderId());
            billDetail.setOccurType(BillDetail.PAY);
            billDetail.setChangeMoney(payOrder.getPayed());
            billDetail.setNowMoney(new BigDecimal(page.getContent().get(0).getNowMoney()).subtract(new BigDecimal(payOrder.getPayed())).setScale(2).doubleValue());
        }else {
            billDetail.setOccurTime(new Date());
            billDetail.setPayOrderId(payOrder.getPayOrderId());
            billDetail.setOccurType(BillDetail.PAY);
            billDetail.setChangeMoney(payOrder.getPayed());
            billDetail.setNowMoney(- new BigDecimal(payOrder.getPayed()).doubleValue());
        }
        billDetailRepository.save(billDetail);
    }

    /**
     * 取消之后的操作
     * @param inOrder
     */
    private void afterCancel(InOrder inOrder){
        PayOrder payOrder = payOrderRepository.findById(inOrder.getPayOrderId()).get();
        PurchaseOrder purchaseOrder = purchaseRepository.findById(payOrder.getPurchaseOrderId()).get();
        payOrder.setStatus(PayOrder.CANCEL);
        purchaseOrder.setStatus(PurchaseOrder.FAILE);
        payOrderRepository.save(payOrder);
        purchaseRepository.save(purchaseOrder);
    }
}

