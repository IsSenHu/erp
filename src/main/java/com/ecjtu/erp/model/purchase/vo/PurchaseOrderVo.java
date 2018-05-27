package com.ecjtu.erp.model.purchase.vo;

import com.ecjtu.erp.model.User;
import com.ecjtu.erp.model.good.CurrencyUnit;
import com.ecjtu.erp.model.purchase.PurchaseOrder;
import com.ecjtu.erp.model.supplier.Supplier;
import com.ecjtu.erp.model.supplier.SupplierGood;

import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 采购单vo
 * @Date: Created in 12:17 2018/5/14
 * @Modified By:
 */
public class PurchaseOrderVo implements Serializable {
    private Long purchaseOrderId;
    private Supplier supplier;
    private SupplierGood good;
    private String createTime;
    private Integer number;
    private Double money;
    private Integer status;
    private CurrencyUnit currencyUnit;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CurrencyUnit getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(CurrencyUnit currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    private PurchaseOrder purchaseOrder;

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public SupplierGood getGood() {
        return good;
    }

    public void setGood(SupplierGood good) {
        this.good = good;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
