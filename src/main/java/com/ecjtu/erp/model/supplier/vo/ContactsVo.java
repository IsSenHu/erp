package com.ecjtu.erp.model.supplier.vo;

import com.ecjtu.erp.model.supplier.Contacts;

import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 18:36 2018/5/6
 * @Modified By:
 */
public class ContactsVo extends Contacts implements Serializable{
    private String supplierName;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
