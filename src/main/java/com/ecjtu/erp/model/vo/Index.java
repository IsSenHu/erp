package com.ecjtu.erp.model.vo;

import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 15:23 2018/5/15
 * @Modified By:
 */
public class Index implements Serializable {
    private Long typeNumber;
    private Double money;
    private Long customerNumber;
    private Long supplierNumber;

    public Long getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(Long typeNumber) {
        this.typeNumber = typeNumber;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Long getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(Long supplierNumber) {
        this.supplierNumber = supplierNumber;
    }
}
