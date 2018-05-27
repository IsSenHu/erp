package com.ecjtu.erp.model.order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: HuSen
 * @Description: 订单
 * @Date: Created in 19:36 2018/5/14
 * @Modified By:
 */
@Entity
@Table(name = "t_order")
public class Order implements Serializable {

    /**
     * 初始，默认状态
     */
    public static final Integer DEFAULT = 0;

    /**
     * 审核通过状态
     */
    public static final Integer CAN = 1;

    /**
     * 审核不通过状态
     */
    public static final Integer CANT = 2;

    /**
     * 订单已完成
     */
    public static final Integer FINISH = 3;

    /**
     * 在线支付
     */
    private static final Integer ONLINE = 11;

    /**
     * 货到付款
     */
    private static final Integer RECEIVE_PAY = 12;

    /**
     * 订单编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    /**
     * 订单状态
     */
    @Column
    private Integer status;
    /**
     * 订单生成时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createTime;

    /**
     * 订单付款方式
     */
    @Column
    @NotNull(message = "订单付款方式不能为空")
    private Integer payType;

    /**
     * 付款使用货币
     */
    @Column
    @NotNull(message = "付款使用货币不能为空")
    private Integer currencyUnitId;

    /**
     * 出库单编号
     */
    @Column
    private Long outOrderId;

    /**
     * 订单总价格
     */
    @Column
    private Double money;

    /**
     * 收款单编号
     */
    @Column
    private Long receiveOrderId;

    /**
     * 备注
     */
    @Column
    private String description;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Long getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(Long outOrderId) {
        this.outOrderId = outOrderId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getReceiveOrderId() {
        return receiveOrderId;
    }

    public void setReceiveOrderId(Long receiveOrderId) {
        this.receiveOrderId = receiveOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
