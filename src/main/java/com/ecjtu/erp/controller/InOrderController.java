package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.purchase.InOrder;
import com.ecjtu.erp.service.InOrderService;
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
 * @Date: Created in 16:11 2018/5/14
 * @Modified By:
 */
@RestController
public class InOrderController {
    private static final Logger log = LoggerFactory.getLogger(InOrderController.class);
    @Autowired
    private InOrderService inOrderService;

    /**
     * 入库单管理页面
     * @param inOrder
     * @return
     */
    @GetMapping("/inOrders")
    private ModelAndView inOrders(InOrder inOrder){
        List<InOrder> list = inOrderService.search(inOrder);
        return new ModelAndView("pages/tables/inOrders")
                .addObject("list", list)
                .addObject("menusShow", inOrderService.menusAndChilds());
    }

    /**
     * 保存入库单
     * @param inOrder
     * @param result
     * @return
     */
    @PostMapping("/saveInOrder")
    private JsonResult saveInOrder(@RequestBody @Valid InOrder inOrder, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return inOrderService.saveInOrder(inOrder);
    }

    /**
     * 设置入库单的状态
     * @param inOrderId
     * @param flag
     * @return
     */
    @PostMapping("/setInOrderStatus")
    private JsonResult setInOrderStatus(Long inOrderId, Integer flag){
        return inOrderService.setInOrderStatus(inOrderId, flag);
    }
}
