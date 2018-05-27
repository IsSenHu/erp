package com.ecjtu.erp.service;

import com.ecjtu.erp.model.good.CurrencyUnit;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 13:36 2018/5/13
 * @Modified By:
 */
public interface CurrencyUnitService extends BaseService {
    List<CurrencyUnit> findAll();

    JsonResult saveCurrencyUnit(CurrencyUnit currencyUnit);

    CurrencyUnit findCurrencyUnitById(Integer currencyUnitId);

    JsonResult deleteCurrencyUnitById(Integer currencyUnitId);

    List<CurrencyUnit> listCurrencyUnit();
}
