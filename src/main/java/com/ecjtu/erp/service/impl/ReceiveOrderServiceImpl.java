package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.billdetail.BillDetail;
import com.ecjtu.erp.model.order.Order;
import com.ecjtu.erp.model.order.OutOrder;
import com.ecjtu.erp.model.order.ReceiveOrder;
import com.ecjtu.erp.model.order.vo.ReceiveOrderVo;
import com.ecjtu.erp.repository.*;
import com.ecjtu.erp.repository.specification.ReceiveOrderSpecification;
import com.ecjtu.erp.service.ReceiveOrderService;
import com.ecjtu.erp.util.JsonResult;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:19 2018/5/14
 * @Modified By:
 */
@Service
public class ReceiveOrderServiceImpl extends BaseServiceImpl implements ReceiveOrderService {
    private static final Logger log = LoggerFactory.getLogger(ReceiveOrderServiceImpl.class);
    @Autowired
    private ReceiveOrderRepository receiveOrderRepository;

    @Autowired
    private CurrencyUnitRepository currencyUnitRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OutOrderRepository outOrderRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public List<ReceiveOrderVo> search(ReceiveOrder receiveOrder) {
        return receiveOrderRepository.findAll(new ReceiveOrderSpecification(receiveOrder))
                .stream().map(receiveOrder1 -> {
                    ReceiveOrderVo vo = new ReceiveOrderVo();
                    BeanUtils.copyProperties(receiveOrder1, vo);
                    vo.setCurrencyUnit(currencyUnitRepository.findById(receiveOrder1.getCurrencyUnitId()).get());
                    return vo;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult receivedMoney(Long receiveOrderId) {
        if(Objects.isNull(receiveOrderId)){
            return new JsonResult(400);
        }
        try {
            ReceiveOrder receiveOrder = receiveOrderRepository.findById(receiveOrderId).get();
            receiveOrder.setStatus(ReceiveOrder.FINISHED);
            receiveOrderRepository.save(receiveOrder);
            taskExecutor.execute(() -> afterReceiveMoney(receiveOrder));
            return new JsonResult(200);
        }catch (Exception e){
            log.error("已收款失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    /**
     * 收款以后要做的事情
     * @param receiveOrder
     */
    private void afterReceiveMoney(ReceiveOrder receiveOrder){
        Order order = orderRepository.findById(receiveOrder.getOrderId()).get();
        OutOrder outOrder = outOrderRepository.findById(order.getOutOrderId()).get();
        order.setStatus(Order.FINISH);
        outOrder.setStatus(OutOrder.RECEIVED);
        outOrder.setReceiveTime(new Date());
        orderRepository.save(order);
        outOrderRepository.save(outOrder);

        //记录收支明细
        BillDetail billDetail = new BillDetail();
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "occurTime"));
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<BillDetail> page = billDetailRepository.findAll(pageable);
        if(CollectionUtils.isNotEmpty(page.getContent())){
            BillDetail last = page.getContent().get(0);
            Double lastMoney = last.getNowMoney();
            billDetail.setChangeMoney(order.getMoney());
            billDetail.setOccurTime(new Date());
            billDetail.setOccurType(BillDetail.RECEIVED);
            billDetail.setReceiveOrderId(receiveOrder.getReceiveOrderId());
            billDetail.setNowMoney(new BigDecimal(lastMoney).add(new BigDecimal(order.getMoney())).setScale(2).doubleValue());
        }else {
            billDetail.setChangeMoney(order.getMoney());
            billDetail.setOccurTime(new Date());
            billDetail.setOccurType(BillDetail.RECEIVED);
            billDetail.setReceiveOrderId(receiveOrder.getReceiveOrderId());
            billDetail.setNowMoney(order.getMoney());
        }
        billDetailRepository.save(billDetail);
    }
}
