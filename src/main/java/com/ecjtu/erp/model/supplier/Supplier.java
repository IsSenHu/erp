package com.ecjtu.erp.model.supplier;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Author: 胡森
 * @Description: 供应商实体类
 * @Date: Created in 13:42 2018/5/6
 * @Modified By:
 */
@Entity
@Table(name = "t_supplier")
public class Supplier implements Serializable {

    /**
     * 未审核
     */
    public static final Integer NODO = 0;
    /**
     * 审核通过
     */
    public static final Integer ENABLED = 1;
    /**
     * 审核不通过，不可用
     */
    public static final Integer DISABLED = 2;
    /**
     * 停用
     */
    public static final Integer STOP = 3;
    /**
     * 处于账期状态
     */
    public static final Integer ACCOUND_PERIOD = 4;

    /**
     * 初始化等级
     */
    public static final Integer DEFAULT_LEVEL = 0;

    /**
     * 主键 供应商编码
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    /**
     * 供应商名称
     */
    @Column
    @NotEmpty(message = "供应商名称不能为空")
    private String name;

    /**
     * 供应商状态
     */
    @Column
    private Integer status;

    /**
     * 供应商描述
     */
    @Column
    private String description;

    /**
     * 供应商等级
     */
    @Column
    private Integer level;

    /**
     * 上次供货时间
     */
    @Column
    private LocalDate lastSupplyDate;

    /**
     * 账期开始日期
     */
    @Column
    private LocalDate accountStartDate;

    /**
     * 账期结束日期
     */
    private LocalDate accountEndDate;

    public LocalDate getAccountStartDate() {
        return accountStartDate;
    }

    public void setAccountStartDate(LocalDate accountStartDate) {
        this.accountStartDate = accountStartDate;
    }

    public LocalDate getAccountEndDate() {
        return accountEndDate;
    }

    public void setAccountEndDate(LocalDate accountEndDate) {
        this.accountEndDate = accountEndDate;
    }

    public LocalDate getLastSupplyDate() {
        return lastSupplyDate;
    }

    public void setLastSupplyDate(LocalDate lastSupplyDate) {
        this.lastSupplyDate = lastSupplyDate;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
