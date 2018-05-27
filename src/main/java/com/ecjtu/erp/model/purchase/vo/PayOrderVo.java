package com.ecjtu.erp.model.purchase.vo;

import com.ecjtu.erp.model.good.CurrencyUnit;

import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 付款单编号
 * @Date: Created in 15:14 2018/5/14
 * @Modified By:
 */
public class PayOrderVo implements Serializable {
    private Long payOrderId;
    private Double need;
    private Double payed;
    private CurrencyUnit currencyUnit;
    private Integer payType;
    private Long purchaseOrderId;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
        this.payOrderId = payOrderId;
    }

    public Double getNeed() {
        return need;
    }

    public void setNeed(Double need) {
        this.need = need;
    }

    public Double getPayed() {
        return payed;
    }

    public void setPayed(Double payed) {
        this.payed = payed;
    }

    public CurrencyUnit getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(CurrencyUnit currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }
}
