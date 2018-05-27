package com.ecjtu.erp.model.good;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 商品规格
 * @Date: Created in 8:35 2018/5/13
 * @Modified By:
 */
@Entity
@Table(name = "t_specification")
public class Specification implements Serializable {
    /**
     * 商品规格编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specificationId;

    /**
     * 规格名称
     */
    @NotBlank(message = "规格名称不能为空")
    private String name;

    /**
     * 规格描述
     */
    private String description;

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
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
        return "Specification{" +
                "specificationId=" + specificationId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
