package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.good.NewGood;
import com.ecjtu.erp.model.supplier.SupplierGood;
import com.ecjtu.erp.model.supplier.vo.SupplierGoodNeed;
import com.ecjtu.erp.model.supplier.vo.SupplierGoodVo;
import com.ecjtu.erp.service.SupplierGoodService;
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
 * @Author: 胡森
 * @Description:
 * @Date: Created in 15:59 2018/5/12
 * @Modified By:
 */
@RestController
public class SupplierGoodController {
    private static final Logger log = LoggerFactory.getLogger(SupplierGoodController.class);

    @Autowired
    private SupplierGoodService supplierGoodService;

    /**
     * 创建供应商商品页面
     * @param newGoodId
     * @return
     */
    @GetMapping("/createSupplierGood")
    private ModelAndView createSupplierGood(Long newGoodId){
        NewGood newGood = supplierGoodService.findNewGoodById(newGoodId);
        return new ModelAndView("pages/examples/supplierAndGood")
                .addObject("good", newGood)
                .addObject("menusShow", supplierGoodService.menusAndChilds());
    }

    /**
     * 获取创建供应商商品所需的所有东西
     * @return
     */
    @GetMapping("/getAllCreateNeed")
    private SupplierGoodNeed getAllCreateNeed(){
        return supplierGoodService.getAllCreateNeed();
    }

    /**
     * 创建供应商商品
     * @param supplierGood
     * @param result
     * @return
     */
    @PostMapping("/saveSupplierGood")
    private JsonResult saveSupplierGood(@RequestBody @Valid SupplierGood supplierGood, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return supplierGoodService.saveSupplierGood(supplierGood);
    }

    /**
     * 供应商品管理
     * @return
     */
    @GetMapping("/supplierGoods")
    private ModelAndView supplierGoods(SupplierGood supplierGood){
        List<SupplierGood> list = supplierGoodService.findAll(supplierGood);
        return new ModelAndView("pages/tables/supplierGoods")
                .addObject("list", list)
                .addObject("menusShow", supplierGoodService.menusAndChilds());
    }

    /**
     * 供应商品管理-供应链经理
     * @return
     */
    @GetMapping("/supplierGoodsFind")
    private ModelAndView supplierGoodsFind(SupplierGood supplierGood){
        List<SupplierGood> list = supplierGoodService.findAll(supplierGood);
        return new ModelAndView("pages/tables/supplierGoodsFind")
                .addObject("list", list)
                .addObject("menusShow", supplierGoodService.menusAndChilds());
    }

    /**
     * 供应商品详细页面
     * @param supplierGoodId
     * @return
     */
    @GetMapping("/detailSupplierGood")
    private ModelAndView detailSupplierGood(Long supplierGoodId){
        SupplierGoodVo vo = supplierGoodService.findSupplierGoodById(supplierGoodId);
        return new ModelAndView("pages/examples/detailSupplierGood")
                .addObject("good", vo)
                .addObject("menusShow", supplierGoodService.menusAndChilds());
    }

    /**
     * 可提供或不可提供该商品
     * @param supplierGoodId
     * @param flag
     * @return
     */
    @PostMapping("/canOrNotCanSupply")
    private JsonResult canOrNotCanSupply(Long supplierGoodId, Integer flag){
        return supplierGoodService.canOrNotCanSupply(supplierGoodId, flag);
    }

    /**
     * 获得所有可以提供出售的商品
     * @return
     */
    @PostMapping("/supplierGoodList")
    private List<SupplierGood> supplierGoodList(){
        return supplierGoodService.supplierGoodList();
    }

    /**
     * 根据id查询商品
     * @param supplierGoodId
     * @return
     */
    @PostMapping("/findSupplierGoodById")
    private SupplierGood findSupplierGoodById(Long supplierGoodId){
        return supplierGoodService.findSupplierGoodById2(supplierGoodId);
    }
}
