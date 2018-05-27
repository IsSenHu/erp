package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.good.NewGood;
import com.ecjtu.erp.service.NewGoodService;
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
 * @Date: Created in 21:32 2018/5/6
 * @Modified By:
 */
@RestController
public class NewGoodController {
    private static final Logger log = LoggerFactory.getLogger(NewGoodController.class);

    @Autowired
    private NewGoodService newGoodService;

    /**
     * 新品管理页面
     * @param newGood
     * @return
     */
    @GetMapping("/newGoods")
    private ModelAndView newGoods(NewGood newGood){
        List<NewGood> list = newGoodService.search(newGood);
        return new ModelAndView("pages/tables/newGoods")
                .addObject("list", list)
                .addObject("menusShow", newGoodService.menusAndChilds());
    }

    /**
     * 保存新品信息
     * @param newGood
     * @param result
     * @return
     */
    @PostMapping("/saveNewGood")
    private JsonResult saveNewGood(@Valid @RequestBody NewGood newGood, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return newGoodService.saveNewGood(newGood);
    }

    /**
     * 根据新品编号来查询新品
     * @param materialId
     * @return
     */
    @PostMapping("/findNewGoodById")
    private NewGood findNewGoodById(Long materialId){
        return newGoodService.findNewGoodById(materialId);
    }

    /**
     * 删除新品
     * @param materialId
     * @return
     */
    @PostMapping("/deleteNewGoodById")
    private JsonResult deleteNewGoodById(Long materialId){
        return newGoodService.deleteNewGoodById(materialId);
    }

    /**
     * 添加到待判断
     * @param materialId
     * @return
     */
    @PostMapping("/add2WaitReview")
    private JsonResult add2WaitReview(Long materialId){
        return newGoodService.add2WaitReview(materialId);
    }

    /**
     * 添加到待核查
     * @param materialId
     * @return
     */
    @PostMapping("/add2WaitCheck")
    private JsonResult add2WaitCheck(Long materialId){
        return newGoodService.add2WaitCheck(materialId);
    }

    /**
     * 检查新品
     * @param materialId
     * @param flag
     * @return
     */
    @PostMapping("/checkNewGood")
    private JsonResult checkNewGood(Long materialId, String flag){
        return newGoodService.checkNewGood(materialId, flag);
    }

    /**
     * 设为可开发或不可开发
     * @param materialId
     * @param flag
     * @return
     */
    @PostMapping("/productOrNotProduct")
    private JsonResult productOrNotProduct(Long materialId, String flag){
        return newGoodService.productOrNotProduct(materialId, flag);
    }
}
