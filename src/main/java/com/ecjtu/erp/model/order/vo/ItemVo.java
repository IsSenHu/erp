package com.ecjtu.erp.model.order.vo;

import com.ecjtu.erp.model.order.Item;
import com.ecjtu.erp.model.supplier.SupplierGood;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 9:53 2018/5/15
 * @Modified By:
 */
public class ItemVo extends Item {
    private SupplierGood good;

    public SupplierGood getGood() {
        return good;
    }

    public void setGood(SupplierGood good) {
        this.good = good;
    }
}
