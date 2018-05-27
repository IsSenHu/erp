package com.ecjtu.erp.model.good;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 商品类型
 * @Date: Created in 10:54 2018/5/13
 * @Modified By:
 */
@Entity
@Table(name = "t_type")
public class Type implements Serializable {
    /**
     * 类型编码
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer typeId;
    /**
     * 名称
     */
    @Column
    @NotBlank(message = "类型名称不能为空")
    private String name;
    /**
     * 描述
     */
    @Column
    private String description;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
        return "Type{" +
                "typeId=" + typeId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
