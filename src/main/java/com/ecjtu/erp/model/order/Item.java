package com.ecjtu.erp.model.order;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 订单条目
 * @Date: Created in 19:52 2018/5/14
 * @Modified By:
 */
@Entity
@Table(name = "t_item")
public class Item implements Serializable {
    /**
     * 条目编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    /**
     * 商品编号
     */
    @Column
    @NotNull(message = "商品编号不能为空")
    private Long supplierGoodId;

    /**
     * 商品数量
     */
    @Column
    @NotNull(message = "商品数量不能为空")
    @Min(value = 0, message = "商品数量不能小于0")
    private Integer number;

    /**
     * 订单总金额
     */
    @Column
    private Double money;

    /**
     * 条目所属订单
     */
    @Column
    @NotNull(message = "订单编号不能为空")
    private Long orderId;

    /**
     * 条目所属出库单
     */
    @Column
    private Long outOrderId;

    public Long getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(Long outOrderId) {
        this.outOrderId = outOrderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
