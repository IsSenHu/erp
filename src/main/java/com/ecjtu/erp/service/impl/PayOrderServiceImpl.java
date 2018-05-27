package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.purchase.PayOrder;
import com.ecjtu.erp.model.purchase.vo.PayOrderVo;
import com.ecjtu.erp.repository.CurrencyUnitRepository;
import com.ecjtu.erp.repository.PayOrderRepository;
import com.ecjtu.erp.repository.specification.PayOrderSpecification;
import com.ecjtu.erp.service.PayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 15:05 2018/5/14
 * @Modified By:
 */
@Service
public class PayOrderServiceImpl extends BaseServiceImpl implements PayOrderService {
    private static final Logger log = LoggerFactory.getLogger(PayOrderServiceImpl.class);

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private CurrencyUnitRepository currencyUnitRepository;

    @Override
    public List<PayOrderVo> search(PayOrder payOrder) {
        return payOrderRepository.findAll(new PayOrderSpecification(payOrder))
                .stream().map(order -> {
                    PayOrderVo vo = new PayOrderVo();
                    vo.setPayOrderId(order.getPayOrderId());
                    vo.setNeed(order.getNeed());
                    vo.setPayed(order.getPayed());
                    vo.setPayType(order.getPayType());
                    vo.setStatus(order.getStatus());
                    vo.setPurchaseOrderId(order.getPurchaseOrderId());
                    vo.setCurrencyUnit(currencyUnitRepository.findById(order.getCurrencyUnitId()).get());
                    return vo;
                }).collect(Collectors.toList());
    }

    @Override
    public List<PayOrder> findAllNoFinishPayOrder() {
        return payOrderRepository.findAllByStatus(PayOrder.NO_FINISH);
    }
}
