package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.good.Brand;
import com.ecjtu.erp.service.BrandService;
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
 * @Date: Created in 10:28 2018/5/13
 * @Modified By:
 */
@RestController
public class BrandController {
    private static final Logger log = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandService brandService;

    /**
     * 品牌管理页面
     * @return
     */
    @GetMapping("/brands")
    private ModelAndView brands(){
        List<Brand> list = brandService.findAll();
        return new ModelAndView("pages/tables/brands")
                .addObject("list", list)
                .addObject("menusShow", brandService.menusAndChilds());
    }

    /**
     * 保存品牌
     * @param brand
     * @param result
     * @return
     */
    @PostMapping("/saveBrand")
    private JsonResult saveBrand(@RequestBody @Valid Brand brand, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return brandService.saveBrand(brand);
    }

    /**
     * 根据id来查询品牌信息
     * @param brandId
     * @return
     */
    @PostMapping("/findBrandById")
    private Brand findBrandById(Integer brandId){
        return brandService.findBrandById(brandId);
    }

    /**
     * 根据id删除品牌
     * @param brandId
     * @return
     */
    @PostMapping("/deleteBrandById")
    private JsonResult deleteBrandById(Integer brandId){
        return brandService.deleteBrandById(brandId);
    }
}
