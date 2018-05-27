package com.ecjtu.erp.model.order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: HuSen
 * @Description: 收款单
 * @Date: Created in 14:33 2018/5/14
 * @Modified By:
 */
@Entity
@Table(name = "t_receive_order")
public class ReceiveOrder implements Serializable {

    /**
     * 已完成
     */
    public static final Integer FINISHED = 1;
    /**
     * 交易中
     */
    public static final Integer ING = 2;
    /**
     * 已取消
     */
    public static final Integer CANCEL = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiveOrderId;
    /**
     * 需收款多少
     */
    @Column
    private Double need;

    /**
     * 已收款多少
     */
    @Column
    private Double received;

    /**
     * 收款货币
     */
    @Column
    private Integer currencyUnitId;

    /**
     * 所属订单
     */
    @Column
    private Long orderId;

    /**
     * 所属采购单编号
     */
    @Column
    private Long purchaseOrderId;

    /**
     * 收款方式
     */
    @Column
    private Integer receiveType;

    /**
     * 收款状态
     */
    @Column
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getReceiveOrderId() {
        return receiveOrderId;
    }

    public void setReceiveOrderId(Long receiveOrderId) {
        this.receiveOrderId = receiveOrderId;
    }

    public Double getNeed() {
        return need;
    }

    public void setNeed(Double need) {
        this.need = need;
    }

    public Double getReceived() {
        return received;
    }

    public void setReceived(Double received) {
        this.received = received;
    }

    public Integer getCurrencyUnitId() {
        return currencyUnitId;
    }

    public void setCurrencyUnitId(Integer currencyUnitId) {
        this.currencyUnitId = currencyUnitId;
    }
}
