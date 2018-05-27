package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.good.CustomsCode;
import com.ecjtu.erp.service.CustomsCodeService;
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
 * @Date: Created in 12:32 2018/5/13
 * @Modified By:
 */
@RestController
public class CustomsCodeController {
    private static final Logger log = LoggerFactory.getLogger(CustomsCodeController.class);

    @Autowired
    private CustomsCodeService customsCodeService;

    /**
     * 海关编码页面
     * @return
     */
    @GetMapping("/customsCodes")
    private ModelAndView customsCodes(){
        List<CustomsCode> list = customsCodeService.findAll();
        return new ModelAndView("pages/tables/customsCodes")
                .addObject("list", list)
                .addObject("menusShow", customsCodeService.menusAndChilds());
    }

    /**
     * 保存海关编码
     * @param customsCode
     * @param result
     * @return
     */
    @PostMapping("/saveCustomsCode")
    private JsonResult saveCustomsCode(@RequestBody @Valid CustomsCode customsCode, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return customsCodeService.saveCustomsCode(customsCode);
    }

    /**
     * 根据id来查询海关编码
     * @param id
     * @return
     */
    @PostMapping("/findCustomsCodeById")
    private CustomsCode findCustomsCodeById(Long id){
        return customsCodeService.findCustomsCodeById(id);
    }

    /**
     * 根据id删除海关编码
     * @param id
     * @return
     */
    @PostMapping("/deleteCustomsCodeById")
    private JsonResult deleteCustomsCodeById(Long id){
        return customsCodeService.deleteCustomsCodeById(id);
    }
}
