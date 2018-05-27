package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.good.Specification;
import com.ecjtu.erp.service.SpecificationService;
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
 * @Date: Created in 8:38 2018/5/13
 * @Modified By:
 */
@RestController
public class SpecificationController {
    private static final Logger log = LoggerFactory.getLogger(SpecificationController.class);
    @Autowired
    private SpecificationService specificationService;

    /**
     * 所有规格页面
     * @return
     */
    @GetMapping("/specifications")
    private ModelAndView specifications(){
        List<Specification> list = specificationService.findAll();
        return new ModelAndView("pages/tables/specifications")
                .addObject("list", list)
                .addObject("menusShow", specificationService.menusAndChilds());
    }

    /**
     * 保存规格
     * @param specification
     * @return
     */
    @PostMapping("/saveSpecification")
    private JsonResult saveSpecification(@RequestBody @Valid Specification specification, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return specificationService.saveSpecification(specification);
    }

    /**
     * 根据ID查询规格
     * @param specificationId
     * @return
     */
    @PostMapping("/findSpecificationById")
    private Specification findSpecificationById(Integer specificationId){
        return specificationService.findSpecificationById(specificationId);
    }

    /**
     * 根据id删除规格
     * @param specificationId
     * @return
     */
    @PostMapping("/deleteSpecificationById")
    private JsonResult deleteSpecificationById(Integer specificationId){
        return specificationService.deleteSpecificationById(specificationId);
    }
}
