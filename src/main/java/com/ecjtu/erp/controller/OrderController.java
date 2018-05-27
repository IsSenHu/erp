package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.order.Item;
import com.ecjtu.erp.model.order.Order;
import com.ecjtu.erp.model.order.vo.ItemVo;
import com.ecjtu.erp.service.OrderService;
import com.ecjtu.erp.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:43 2018/5/14
 * @Modified By:
 */
@RestController
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 订单页面
     * @param order
     * @return
     */
    @GetMapping("/orders")
    private ModelAndView orders(Order order){
        List<Order> list = orderService.search(order);
        return new ModelAndView("pages/tables/orders")
                .addObject("list", list)
                .addObject("menusShow", orderService.menusAndChilds());
    }

    /**
     * 保存订单
     * @param order
     * @param result
     * @return
     */
    @PostMapping("/saveOrder")
    private JsonResult saveOrder(@RequestBody @Valid Order order, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return orderService.saveOrder(order);
    }

    /**
     * 新增条目
     * @param item
     * @param result
     * @return
     */
    @PostMapping("/saveItem")
    private JsonResult saveItem(@RequestBody @Valid Item item, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return orderService.saveItem(item);
    }

    /**
     * 根据订单号查询条目
     * @param orderId
     * @return
     */
    @PostMapping("/findAllItemByOrderId")
    private List<ItemVo> findAllItemByOrderId(Long orderId){
        return orderService.findAllItemByOrderId(orderId);
    }

    /**
     * @param orderId
     * @param flag
     * @return
     */
    @PostMapping("/setOrderStatus")
    private JsonResult setOrderStatus(Long orderId, Integer flag){
        return orderService.setOrderStatus(orderId, flag);
    }
}
