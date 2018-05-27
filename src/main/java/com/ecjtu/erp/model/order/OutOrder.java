package com.ecjtu.erp.model.order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: HuSen
 * @Description: 出库单
 * @Date: Created in 10:31 2018/5/15
 * @Modified By:
 */
@Entity
@Table(name = "t_outorder")
public class OutOrder implements Serializable {

    /**
     * 准备中
     */
    public static final Integer PREPAER = 0;

    /**
     * 已发货
     */
    public static final Integer DELIEVERED = 1;

    /**
     * 已收货
     */
    public static final Integer RECEIVED = 2;

    /**
     * 出库单编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outOrderId;

    /**
     * 出库时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date outTime;

    /**
     * 收货时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date receiveTime;

    /**
     * 出库总金额
     */
    @Column
    private Double money;

    /**
     * 所属订单
     */
    @Column
    private Long orderId;

    /**
     * 出库单状态
     */
    @Column
    private Integer status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Long getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(Long outOrderId) {
        this.outOrderId = outOrderId;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
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
