package com.ecjtu.erp.model.supplier;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description: 联系人实体类
 * @Date: Created in 13:46 2018/5/6
 * @Modified By:
 */
@Entity
@Table(name = "t_contacts")
public class Contacts implements Serializable {
    /**
     * 联系人编码
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactsId;

    /**
     * 联系人名称
     */
    @Column
    @NotEmpty(message = "联系人名称不能为空")
    private String name;

    /**
     * 联系人手机号
     */
    @Column
    @NotEmpty(message = "手机号不能为空")
    private String phone;

    /**
     * 联系人邮箱
     */
    @Column
    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 所属供应商ID
     */
    @Column
    @NotNull(message = "供应商不能为空")
    private Long supplierId;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getContactsId() {
        return contactsId;
    }

    public void setContactsId(Long contactsId) {
        this.contactsId = contactsId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
