package com.ecjtu.erp.model.billdetail;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: HuSen
 * @Description: 收支明细表
 * @Date: Created in 19:57 2018/5/14
 * @Modified By:
 */
@Entity
@Table(name = "t_bill_detail")
public class BillDetail implements Serializable {
    public static final Integer RECEIVED = 1;
    public static final Integer PAY = 2;
    /**
     * 明细编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billDetailId;

    /**
     * 发生时间
     */
    @Column
    private Date occurTime;

    /**
     * 发生类型
     */
    @Column
    private Integer occurType;

    /**
     * 现在的收入情况
     */
    @Column
    private Double nowMoney;

    /**
     * 付款单编号
     */
    @Column
    private Long payOrderId;

    /**
     * 收款单编号
     */
    @Column
    private Long receiveOrderId;

    /**
     * 变化的金额
     */
    @Column
    private Double changeMoney;

    public Double getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(Double changeMoney) {
        this.changeMoney = changeMoney;
    }

    public Long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(Long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public Integer getOccurType() {
        return occurType;
    }

    public void setOccurType(Integer occurType) {
        this.occurType = occurType;
    }

    public Double getNowMoney() {
        return nowMoney;
    }

    public void setNowMoney(Double nowMoney) {
        this.nowMoney = nowMoney;
    }

    public Long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
        this.payOrderId = payOrderId;
    }

    public Long getReceiveOrderId() {
        return receiveOrderId;
    }

    public void setReceiveOrderId(Long receiveOrderId) {
        this.receiveOrderId = receiveOrderId;
    }
}
