package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.purchase.PayOrder;
import com.ecjtu.erp.model.purchase.vo.PayOrderVo;
import com.ecjtu.erp.service.PayOrderService;
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
 * @Date: Created in 15:04 2018/5/14
 * @Modified By:
 */
@RestController
public class PayOrderController {
    private static final Logger log = LoggerFactory.getLogger(PayOrderController.class);

    @Autowired
    private PayOrderService payOrderService;

    /**
     * 付款单页面
     * @param payOrder
     * @return
     */
    @GetMapping("/payOrders")
    private ModelAndView payOrders(PayOrder payOrder){
        List<PayOrderVo> list = payOrderService.search(payOrder);
        return new ModelAndView("pages/tables/payOrders")
                .addObject("list", list)
                .addObject("menusShow", payOrderService.menusAndChilds());
    }

    /**
     * 插叙所有的未完成的付款单
     * @return
     */
    @PostMapping("/findAllNoFinishPayOrder")
    private List<PayOrder> findAllNoFinishPayOrder(){
        return payOrderService.findAllNoFinishPayOrder();
    }

    //todo 扫码支付的方式
}
