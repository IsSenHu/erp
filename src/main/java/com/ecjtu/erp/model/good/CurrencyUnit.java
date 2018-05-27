package com.ecjtu.erp.model.good;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @Author: HuSen
 * @Description: 货币单位
 * @Date: Created in 13:34 2018/5/13
 * @Modified By:
 */
@Entity
@Table(name = "t_currency_unit")
public class CurrencyUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer currencyUnitId;
    @Column
    @NotBlank(message = "单位名称不能为空")
    private String name;
    @Column
    private String description;

    public Integer getCurrencyUnitId() {
        return currencyUnitId;
    }

    public void setCurrencyUnitId(Integer currencyUnitId) {
        this.currencyUnitId = currencyUnitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CurrencyUnit{" +
                "currencyUnitId=" + currencyUnitId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
