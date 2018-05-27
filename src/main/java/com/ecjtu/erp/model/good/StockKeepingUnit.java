package com.ecjtu.erp.model.good;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 库存量单位
 * @Date: Created in 9:48 2018/5/13
 * @Modified By:
 */
@Entity
@Table(name = "t_stock_keep_unit")
public class StockKeepingUnit implements Serializable {

    /**
     * 库存量单位Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer unitId;

    /**
     * sku
     */
    @Column
    @NotBlank(message = "sku编码不能为空")
    private String sku;

    /**
     * 描述
     */
    @Column
    private String description;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "StockKeepingUnit{" +
                "unitId=" + unitId +
                ", sku='" + sku + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
