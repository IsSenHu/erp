package com.ecjtu.erp.service;

import com.ecjtu.erp.model.good.StockKeepingUnit;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 9:55 2018/5/13
 * @Modified By:
 */
public interface StockKeepingUnitService extends BaseService {
    List<StockKeepingUnit> findAll();

    JsonResult saveStockKeepUnit(StockKeepingUnit stockKeepingUnit);

    StockKeepingUnit findStockKeepUnitById(Integer unitId);

    JsonResult deleteStockKeepUnitById(Integer unitId);
}
