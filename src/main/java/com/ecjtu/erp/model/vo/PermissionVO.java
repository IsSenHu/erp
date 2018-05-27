package com.ecjtu.erp.model.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 23:02 2018/4/30
 * @Modified By:
 */
public class PermissionVO implements Serializable {
    private Long permissionId;
    @NotEmpty(message = "权限名称不能为空")
    @Length(max = 32, message = "权限名称的长度不能大于32")
    private String name;
    private String description;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
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

    public PermissionVO() {
        super();
    }

    public PermissionVO(Long permissionId, String name, String description) {
        super();
        this.permissionId = permissionId;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "PermissionVO{" +
                "permissionId=" + permissionId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
