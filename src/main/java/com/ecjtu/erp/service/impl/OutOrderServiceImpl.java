package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.order.OutOrder;
import com.ecjtu.erp.repository.OutOrderRepository;
import com.ecjtu.erp.repository.specification.OutOrderSpecification;
import com.ecjtu.erp.service.OutOrderService;
import com.ecjtu.erp.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 11:07 2018/5/15
 * @Modified By:
 */
@Service
public class OutOrderServiceImpl extends BaseServiceImpl implements OutOrderService {
    private static final Logger log = LoggerFactory.getLogger(OutOrderServiceImpl.class);

    @Autowired
    private OutOrderRepository outOrderRepository;

    @Override
    public List<OutOrder> search(OutOrder outOrder) {
        return outOrderRepository.findAll(new OutOrderSpecification(outOrder));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult outStock(Long outOrderId) {
        if(Objects.isNull(outOrderId)){
            return new JsonResult(400);
        }
        try {
            OutOrder outOrder = outOrderRepository.findById(outOrderId).get();
            if(!OutOrder.PREPAER.equals(outOrder.getStatus())){
                return new JsonResult(400);
            }
            outOrder.setStatus(OutOrder.DELIEVERED);
            outOrder.setOutTime(new Date());
            outOrderRepository.save(outOrder);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("出库失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
