package com.ecjtu.erp.model.custom;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 客户
 * @Date: Created in 19:45 2018/5/14
 * @Modified By:
 */
@Entity
@Table(name = "t_customer")
public class Customer implements Serializable {
    /**
     * 客户编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    /**
     * 客户名称
     */
    @Column
    @NotBlank(message = "客户名称不能为空")
    private String name;

    /**
     * 收货人电话
     */
    @Column
    @NotBlank(message = "收货人电话不能为空")
    private String phone;

    /**
     * 收货人地址
     */
    @Column
    @NotBlank(message = "收货人地址不能为空")
    private String address;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
