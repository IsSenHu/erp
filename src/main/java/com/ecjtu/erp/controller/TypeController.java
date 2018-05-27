package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.good.Type;
import com.ecjtu.erp.service.TypeService;
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
 * @Date: Created in 13:13 2018/5/13
 * @Modified By:
 */
@RestController
public class TypeController {
    private static final Logger log = LoggerFactory.getLogger(TypeController.class);
    @Autowired
    private TypeService typeService;

    /**
     * 商品类型页面
     * @return
     */
    @GetMapping("/types")
    private ModelAndView types(){
        List<Type> list = typeService.findAll();
        return new ModelAndView("pages/tables/types")
                .addObject("list", list)
                .addObject("menusShow", typeService.menusAndChilds());
    }

    /**
     * 保存商品类型
     * @param type
     * @param result
     * @return
     */
    @PostMapping("/saveType")
    private JsonResult saveType(@Valid @RequestBody Type type, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return typeService.saveType(type);
    }

    /**
     * 根据id查询商品类型
     * @param typeId
     * @return
     */
    @PostMapping("/findTypeById")
    private Type findTypeById(Integer typeId){
        return typeService.findTypeById(typeId);
    }

    /**
     * 根据id删除类型
     * @param typeId
     * @return
     */
    @PostMapping("/deleteTypeById")
    private JsonResult deleteTypeById(Integer typeId){
        return typeService.deleteTypeById(typeId);
    }
}
