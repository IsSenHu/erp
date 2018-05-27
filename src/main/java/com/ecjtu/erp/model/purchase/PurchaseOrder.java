package com.ecjtu.erp.model.purchase;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: 胡森
 * @Description: 采购单
 * @Date: Created in 0:29 2018/5/10
 * @Modified By:
 */
@Entity
@Table(name = "t_purchase_order")
public class PurchaseOrder implements Serializable {

    /**
     * 初始、申请状态
     */
    public static final Integer DEFAULT = 0;

    /**
     * 可以订货
     */
    public static final Integer CAN = 1;

    /**
     * 无法订货
     */
    public static final Integer CANT = 2;

    /**
     * 采购完成
     */
    public static final Integer FINISH = 3;

    /**
     * 采购失败
     */
    public static final Integer FAILE = 4;

    /**
     * 采购中
     */
    public static final Integer PROCESSING = 5;

    /**
     * 货到付款
     */
    public static final Integer PAY_ONE = 11;

    /**
     * 在线支付
     */
    public static final Integer PAY_TWO = 12;

    /**
     * 采购单编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrderId;

    /**
     * 采购员编号
     */
    @Column
    private Long purchaserId;

    /**
     * 创建时间
     */
    @Column
    private LocalDateTime createTime;

    /**
     * 供应商编号
     */
    @Column
    private Long supplierId;

    /**
     * 采购商品编号
     */
    @Column
    @NotNull(message = "采购商品编号不能为空")
    private Long supplierGoodId;

    /**
     * 采购数量
     */
    @Column
    @NotNull(message = "采购数量不能为空")
    @Min(value = 0, message = "采购数量不能小于0")
    private Integer number;

    /**
     * 采购金额
     */
    @Column
    private Double money;

    /**
     * 采购付款方式
     */
    @Column
    private Integer payType;

    /**
     * 采购使用货币
     */
    @Column
    private Integer currencyUnitId;

    /**
     * 到货通知单号
     */
    @Column
    private Long inOrderId;

    /**
     * 状态
     */
    @Column
    private Integer status;

    /**
     * 描述
     */
    @Column
    private String description;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getSupplierGoodId() {
        return supplierGoodId;
    }

    public void setSupplierGoodId(Long supplierGoodId) {
        this.supplierGoodId = supplierGoodId;
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

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getCurrencyUnitId() {
        return currencyUnitId;
    }

    public void setCurrencyUnitId(Integer currencyUnitId) {
        this.currencyUnitId = currencyUnitId;
    }

    public Long getInOrderId() {
        return inOrderId;
    }

    public void setInOrderId(Long inOrderId) {
        this.inOrderId = inOrderId;
    }
}
