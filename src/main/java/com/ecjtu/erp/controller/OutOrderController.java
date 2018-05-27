package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.order.OutOrder;
import com.ecjtu.erp.service.OutOrderService;
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
 * @Date: Created in 11:06 2018/5/15
 * @Modified By:
 */
@RestController
public class OutOrderController {

    private static final Logger log = LoggerFactory.getLogger(OutOrderController.class);

    @Autowired
    private OutOrderService outOrderService;

    /**
     * 出库单页面
     * @param outOrder
     * @return
     */
    @GetMapping("/outOrders")
    private ModelAndView outOrders(OutOrder outOrder){
        List<OutOrder> list = outOrderService.search(outOrder);
        return new ModelAndView("pages/tables/outOrders")
                .addObject("list", list)
                .addObject("menusShow", outOrderService.menusAndChilds());
    }

    /**
     * 出库
     * @param outOrderId
     * @return
     */
    @PostMapping("/outStock")
    private JsonResult outStock(Long outOrderId){
        return outOrderService.outStock(outOrderId);
    }
}
