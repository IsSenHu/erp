package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.good.CurrencyUnit;
import com.ecjtu.erp.service.CurrencyUnitService;
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
 * @Date: Created in 13:35 2018/5/13
 * @Modified By:
 */
@RestController
public class CurrencyUnitController {
    private static final Logger log = LoggerFactory.getLogger(CurrencyUnitController.class);
    @Autowired
    private CurrencyUnitService currencyUnitService;

    /**
     * 货币单位页面
     * @return
     */
    @GetMapping("/currencyUnits")
    private ModelAndView currencyUnits(){
        List<CurrencyUnit> list = currencyUnitService.findAll();
        return new ModelAndView("pages/tables/currencyUnits")
                .addObject("list", list)
                .addObject("menusShow", currencyUnitService.menusAndChilds());
    }

    /**
     * 保存货币单位
     * @param currencyUnit
     * @param result
     * @return
     */
    @PostMapping("/saveCurrencyUnit")
    private JsonResult saveCurrencyUnit(@RequestBody @Valid CurrencyUnit currencyUnit, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return currencyUnitService.saveCurrencyUnit(currencyUnit);
    }

    /**
     * 根据id查询货币单位
     * @param currencyUnitId
     * @return
     */
    @PostMapping("/findCurrencyUnitById")
    private CurrencyUnit findCurrencyUnitById(Integer currencyUnitId){
        return currencyUnitService.findCurrencyUnitById(currencyUnitId);
    }

    /**
     * 根据id删除货币单位
     * @param currencyUnitId
     * @return
     */
    @PostMapping("/deleteCurrencyUnitById")
    private JsonResult deleteCurrencyUnitById(Integer currencyUnitId){
        return currencyUnitService.deleteCurrencyUnitById(currencyUnitId);
    }

    /**
     * 查询所有的货币单位
     * @return
     */
    @PostMapping("/listCurrencyUnit")
    public List<CurrencyUnit> listCurrencyUnit(){
        return currencyUnitService.listCurrencyUnit();
    }
}
