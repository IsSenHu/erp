package com.ecjtu.erp.model.purchase;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: HuSen
 * @Description: 到货单，入库单
 * @Date: Created in 10:01 2018/5/14
 * @Modified By:
 */
@Entity
@Table(name = "t_inorder")
public class InOrder implements Serializable {

    /**
     * 等待中
     */
    public static final Integer DEFAULT = 0;

    /**
     * 审核通过
     */
    public static final Integer ACCESS = 1;

    /**
     * 审核不通过
     */
    public static final Integer NO_ACCESS = 2;

    /**
     * 已取消
     */
    public static final Integer CANCEL = 3;

    /**
     * 入库单号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inOrderId;

    /**
     * 到货数量
     */
    @Column
    @NotNull(message = "到货数量不能为空")
    @Min(value = 0, message = "到货数量不能少于0")
    private Integer number;

    /**
     * 到货时间
     */
    @Column
    private LocalDateTime inTime;

    /**
     * 质检情况
     */
    @Column
    @NotNull(message = "质检情况不能为空")
    @DecimalMin(value = "0", message = "质检情况不能小于0")
    @DecimalMax(value = "100", message = "质检情况不能大于100")
    private Double qT;

    /**
     * 状态
     */
    @Column
    private Integer status;

    /**
     * 已入库数量
     */
    @Column
    @NotNull(message = "已入库数量不能为空")
    @Min(value = 0, message = "已入库数量不能小于0")
    private Integer inNumber;

    /**
     * 入库完成时间
     */
    @Column
    private LocalDateTime finishTime;

    /**
     * 采购完成时间
     */
    @Column
    private LocalDateTime finishPurchase;

    /**
     * 所属付款单号
     */
    @Column
    @NotNull(message = "所属付款单号不能为空")
    private Long payOrderId;
    /**
     * 其他描述
     */
    @Column
    private String description;

    public Long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
        this.payOrderId = payOrderId;
    }

    public Long getInOrderId() {
        return inOrderId;
    }

    public void setInOrderId(Long inOrderId) {
        this.inOrderId = inOrderId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDateTime getInTime() {
        return inTime;
    }

    public void setInTime(LocalDateTime inTime) {
        this.inTime = inTime;
    }

    public Double getqT() {
        return qT;
    }

    public void setqT(Double qT) {
        this.qT = qT;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInNumber() {
        return inNumber;
    }

    public void setInNumber(Integer inNumber) {
        this.inNumber = inNumber;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public LocalDateTime getFinishPurchase() {
        return finishPurchase;
    }

    public void setFinishPurchase(LocalDateTime finishPurchase) {
        this.finishPurchase = finishPurchase;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
