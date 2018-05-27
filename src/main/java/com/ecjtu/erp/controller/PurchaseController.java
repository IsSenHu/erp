package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.purchase.PurchaseOrder;
import com.ecjtu.erp.model.purchase.vo.PurchaseOrderVo;
import com.ecjtu.erp.service.PurchaseService;
import com.ecjtu.erp.service.impl.BaseServiceImpl;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 10:21 2018/5/14
 * @Modified By:
 */
@RestController
public class PurchaseController {
    private static final Logger log = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    private PurchaseService purchaseService;

    /**
     * 采购单页面
     * @param vo
     * @return
     */
    @GetMapping("purchaseOrders")
    private ModelAndView purchaseOrders(PurchaseOrderVo vo){
        List<PurchaseOrderVo> list = purchaseService.search(vo);
        return new ModelAndView("pages/tables/purchaseOrders")
                .addObject("list", list)
                .addObject("menusShow", purchaseService.menusAndChilds());
    }

    /**
     * 创建采购申请
     * @param purchaseOrder
     * @param result
     * @return
     */
    @PostMapping("/createPurchaseOrder")
    private JsonResult createPurchaseOrder(@RequestBody @Valid PurchaseOrder purchaseOrder, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return purchaseService.createPurchaseOrder(purchaseOrder);
    }

    /**
     * 设置是否能够采购
     * @param purchaseOrderId
     * @param flag
     * @return
     */
    @PostMapping("/canOrNotCanPurchase")
    private JsonResult canOrNotCanPurchase(Long purchaseOrderId, Integer flag){
        return purchaseService.canOrNotCanPurchase(purchaseOrderId, flag);
    }

    /**
     * 根据id查询采购单详细信息
     * @param purchaseOrderId
     * @return
     */
    @PostMapping("/findPurchaseOrderById")
    private PurchaseOrderVo findPurchaseOrderById(Long purchaseOrderId){
        return purchaseService.findPurchaseOrderById(purchaseOrderId);
    }

    /**
     * 进行采购 货到付款的方式
     * @param purchaseOrderId
     * @return
     */
    @PostMapping("/purchasing")
    private JsonResult purchasing(Long purchaseOrderId){
        return purchaseService.purchasing(purchaseOrderId, BaseServiceImpl.getUser());
    }
}
