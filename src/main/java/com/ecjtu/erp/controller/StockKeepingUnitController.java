package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.good.StockKeepingUnit;
import com.ecjtu.erp.service.StockKeepingUnitService;
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
 * @Date: Created in 9:54 2018/5/13
 * @Modified By:
 */
@RestController
public class StockKeepingUnitController {
    private static final Logger log = LoggerFactory.getLogger(StockKeepingUnitController.class);

    @Autowired
    private StockKeepingUnitService stockKeepingUnitService;

    /**
     * 库存量单位页面
     * @return
     */
    @GetMapping("/stockKeepingUnits")
    private ModelAndView stockKeepingUnits(){
        List<StockKeepingUnit> list = stockKeepingUnitService.findAll();
        return new ModelAndView("pages/tables/stockKeepUnits")
                .addObject("list", list)
                .addObject("menusShow", stockKeepingUnitService.menusAndChilds());
    }

    /**
     * 保存sku
     * @param stockKeepingUnit
     * @param result
     * @return
     */
    @PostMapping("/saveStockKeepUnit")
    private JsonResult saveStockKeepUnit(@RequestBody @Valid StockKeepingUnit stockKeepingUnit, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return stockKeepingUnitService.saveStockKeepUnit(stockKeepingUnit);
    }

    /**
     * 根据Id查询sku
     * @param unitId
     * @return
     */
    @PostMapping("/findStockKeepUnitById")
    private StockKeepingUnit findStockKeepUnitById(Integer unitId){
        return stockKeepingUnitService.findStockKeepUnitById(unitId);
    }

    /**
     * 根据id删除sku
     * @param unitId
     * @return
     */
    @PostMapping("/deleteStockKeepUnitById")
    private JsonResult deleteStockKeepUnitById(Integer unitId){
        return stockKeepingUnitService.deleteStockKeepUnitById(unitId);
    }
}
