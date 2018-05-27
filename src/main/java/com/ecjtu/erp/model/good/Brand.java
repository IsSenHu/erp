package com.ecjtu.erp.model.good;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 商品品牌
 * @Date: Created in 10:26 2018/5/13
 * @Modified By:
 */
@Entity
@Table(name = "t_brand")
public class Brand implements Serializable {
    /**
     * 商品编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brandId;

    /**
     * 品牌名称
     */
    @Column
    @NotBlank(message = "品牌名称不能为空")
    private String name;
    /**
     * 品牌描述
     */
    @Column
    private String description;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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
        return "Brand{" +
                "brandId=" + brandId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
