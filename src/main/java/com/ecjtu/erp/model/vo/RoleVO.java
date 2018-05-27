package com.ecjtu.erp.model.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 20:01 2018/4/30
 * @Modified By:
 */
public class RoleVO implements Serializable {
    private Integer roleId;
    @NotEmpty(message = "角色名不能为空")
    @Length(max = 20, message = "长度不能大于20")
    private String name;
    private String description;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

    public RoleVO() {
        super();
    }

    public RoleVO(Integer roleId, @NotEmpty(message = "角色名不能为空") @Length(max = 20, message = "长度不能大于20") String name, String description) {
        super();
        this.roleId = roleId;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "RoleVO{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
