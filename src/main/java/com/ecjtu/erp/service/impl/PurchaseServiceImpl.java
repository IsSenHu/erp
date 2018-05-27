package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.User;
import com.ecjtu.erp.model.purchase.PayOrder;
import com.ecjtu.erp.model.purchase.PurchaseOrder;
import com.ecjtu.erp.model.purchase.vo.PurchaseOrderVo;
import com.ecjtu.erp.model.supplier.SupplierGood;
import com.ecjtu.erp.repository.*;
import com.ecjtu.erp.repository.specification.PurchaseOrderSpecification;
import com.ecjtu.erp.service.PurchaseService;
import com.ecjtu.erp.util.JsonResult;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 10:41 2018/5/14
 * @Modified By:
 */
@Service
public class PurchaseServiceImpl extends BaseServiceImpl implements PurchaseService {
    private static final Logger log = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    @Autowired
    private SupplierGoodRepository supplierGoodRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private CurrencyUnitRepository currencyUnitRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult createPurchaseOrder(PurchaseOrder purchaseOrder) {
        try {
            //创建时间
            purchaseOrder.setCreateTime(LocalDateTime.now());
            SupplierGood supplierGood = supplierGoodRepository.findById(purchaseOrder.getSupplierGoodId()).get();
            //供应商编号
            purchaseOrder.setSupplierId(supplierGood.getSupplierId());
            //采购金额
            BigDecimal money = new BigDecimal(supplierGood.getBuy()).multiply(new BigDecimal(purchaseOrder.getNumber())).setScale(2);
            purchaseOrder.setMoney(money.doubleValue());
            //采购使用货币
            purchaseOrder.setCurrencyUnitId(supplierGood.getCurrencyUnitId());
            //状态
            purchaseOrder.setStatus(PurchaseOrder.DEFAULT);
            purchaseRepository.save(purchaseOrder);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("创建采购单失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<PurchaseOrderVo> search(PurchaseOrderVo vo) {
        return purchaseRepository.findAll(new PurchaseOrderSpecification(vo)).stream()
                .map(purchaseOrder -> {
                    PurchaseOrderVo purchaseOrderVo = new PurchaseOrderVo();
                    purchaseOrderVo.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
                    purchaseOrderVo.setStatus(purchaseOrder.getStatus());
                    purchaseOrderVo.setCreateTime(DateFormatUtils.format(Date.from(purchaseOrder.getCreateTime().atZone(ZoneId.systemDefault()).toInstant()), "yyyy-MM-dd HH:mm:ss"));
                    purchaseOrderVo.setNumber(purchaseOrder.getNumber());
                    SupplierGood good = supplierGoodRepository.findById(purchaseOrder.getSupplierGoodId()).get();
                    purchaseOrderVo.setGood(good);
                    purchaseOrderVo.setMoney(new BigDecimal(good.getBuy()).multiply(new BigDecimal(purchaseOrder.getNumber())).setScale(2).doubleValue());
                    purchaseOrderVo.setSupplier(supplierRepository.findById(purchaseOrder.getSupplierId()).get());
                    purchaseOrderVo.setCurrencyUnit(currencyUnitRepository.findById(purchaseOrder.getCurrencyUnitId()).get());
                    return purchaseOrderVo;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult canOrNotCanPurchase(Long purchaseOrderId, Integer flag) {
        if(Objects.isNull(purchaseOrderId) || Objects.isNull(flag)){
            return new JsonResult(400);
        }
        try {
            PurchaseOrder purchaseOrder = purchaseRepository.findById(purchaseOrderId).get();
            if(PurchaseOrder.DEFAULT.equals(purchaseOrder.getStatus()) || PurchaseOrder.CAN.equals(purchaseOrder.getStatus()) || PurchaseOrder.CANT.equals(purchaseOrder.getStatus())){
                if(PurchaseOrder.CAN.equals(flag)){
                    purchaseOrder.setStatus(PurchaseOrder.CAN);
                }else if (PurchaseOrder.CANT.equals(flag)){
                    purchaseOrder.setStatus(PurchaseOrder.CANT);
                }else if(PurchaseOrder.DEFAULT.equals(flag)){
                    purchaseOrder.setStatus(PurchaseOrder.DEFAULT);
                }
                purchaseRepository.save(purchaseOrder);
                return new JsonResult(200);
            }else {
                return new JsonResult(400);
            }
        }catch (Exception e){
            log.error("设置采购单状态失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public PurchaseOrderVo findPurchaseOrderById(Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseRepository.findById(purchaseOrderId).get();
        PurchaseOrderVo purchaseOrderVo = new PurchaseOrderVo();
        purchaseOrderVo.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        purchaseOrderVo.setStatus(purchaseOrder.getStatus());
        purchaseOrderVo.setCreateTime(DateFormatUtils.format(Date.from(purchaseOrder.getCreateTime().atZone(ZoneId.systemDefault()).toInstant()), "yyyy-MM-dd HH:mm:ss"));
        purchaseOrderVo.setNumber(purchaseOrder.getNumber());
        SupplierGood good = supplierGoodRepository.findById(purchaseOrder.getSupplierGoodId()).get();
        purchaseOrderVo.setGood(good);
        purchaseOrderVo.setMoney(new BigDecimal(good.getBuy()).multiply(new BigDecimal(purchaseOrder.getNumber())).setScale(2).doubleValue());
        purchaseOrderVo.setSupplier(supplierRepository.findById(purchaseOrder.getSupplierId()).get());
        purchaseOrderVo.setPurchaseOrder(purchaseOrder);
        purchaseOrderVo.setCurrencyUnit(currencyUnitRepository.findById(purchaseOrder.getCurrencyUnitId()).get());
        purchaseOrderVo.setUser(Objects.isNull(purchaseOrder.getPurchaserId()) ? new User() : userRepository.findById(purchaseOrder.getPurchaserId()).get());
        return purchaseOrderVo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult purchasing(Long purchaseOrderId, User user) {
        try {
            PurchaseOrder purchaseOrder = purchaseRepository.findById(purchaseOrderId).get();
            purchaseOrder.setStatus(PurchaseOrder.PROCESSING);
            purchaseOrder.setPurchaserId(user.getUserId());
            //生成付款单
            PayOrder payOrder = new PayOrder();
            payOrder.setCurrencyUnitId(purchaseOrder.getCurrencyUnitId());
            payOrder.setNeed(purchaseOrder.getMoney());
            payOrder.setPurchaseOrderId(purchaseOrderId);
            payOrder.setPayed(0.00);
            payOrder.setPayType(purchaseOrder.getPayType());
            payOrder.setStatus(PayOrder.NO_FINISH);
            payOrderRepository.save(payOrder);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("采购失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
