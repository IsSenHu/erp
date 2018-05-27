package com.ecjtu.erp.model.purchase;

import com.ecjtu.erp.model.purchase.vo.PayOrderVo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 付款单
 * @Date: Created in 14:22 2018/5/14
 * @Modified By:
 */
@Entity
@Table(name = "t_pay_order")
public class PayOrder implements Serializable {
    /**
     * 已完成
     */
    public static final Integer FINISH = 1;
    /**
     * 未完成
     */
    public static final Integer NO_FINISH = 2;
    /**
     * 已取消
     */
    public static final Integer CANCEL = 3;
    /**
     * 已绑定
     */
    public static final Integer BINDED = 4;
    /**
     * 付款单编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payOrderId;

    /**
     * 需支付多少
     */
    @Column
    private Double need;

    /**
     * 已支付多少
     */
    @Column
    private Double payed;

    /**
     * 付款货币
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
     * 付款方式
     */
    @Column
    private Integer payType;

    /**
     * 付款单状态
     */
    @Column
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
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

    public Integer getCurrencyUnitId() {
        return currencyUnitId;
    }

    public void setCurrencyUnitId(Integer currencyUnitId) {
        this.currencyUnitId = currencyUnitId;
    }
}
