package com.ecjtu.erp.model.order.vo;

import com.ecjtu.erp.model.good.CurrencyUnit;
import com.ecjtu.erp.model.order.ReceiveOrder;

import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 11:47 2018/5/15
 * @Modified By:
 */
public class ReceiveOrderVo extends ReceiveOrder implements Serializable {
    private CurrencyUnit currencyUnit;

    public CurrencyUnit getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(CurrencyUnit currencyUnit) {
        this.currencyUnit = currencyUnit;
    }
}
