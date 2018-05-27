package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.order.ReceiveOrder;
import com.ecjtu.erp.model.order.vo.ReceiveOrderVo;
import com.ecjtu.erp.service.ReceiveOrderService;
import com.ecjtu.erp.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:18 2018/5/14
 * @Modified By:
 */
@RestController
public class ReceiveOrderController {
    private static final Logger log = LoggerFactory.getLogger(ReceiveOrderController.class);

    @Autowired
    private ReceiveOrderService receiveOrderService;

    /**
     * 收款单页面
     * @param receiveOrder
     * @return
     */
    @GetMapping("/receiveOrders")
    private ModelAndView receiverOrders(ReceiveOrder receiveOrder){
        List<ReceiveOrderVo> list = receiveOrderService.search(receiveOrder);
        return new ModelAndView("pages/tables/receiveOrders")
                .addObject("list", list)
                .addObject("menusShow", receiveOrderService.menusAndChilds());
    }

    /**
     * 以收款
     * @param receiveOrderId
     * @return
     */
    @PostMapping("/receivedMoney")
    private JsonResult receivedMoney(Long receiveOrderId){
        return receiveOrderService.receivedMoney(receiveOrderId);
    }
}