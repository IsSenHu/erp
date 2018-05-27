package com.ecjtu.erp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: 胡森
 * @Description: 角色
 * @Date: Created in 14:08 2018/4/30
 * @Modified By:
 */
@Entity
@Table(name = "t_role")
public class Role implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    /**
     * 角色名字
     */
    @Column
    private String name;

    /**
     * 角色描述
     */
    @Column
    private String description;

    /**
     * 该角色所拥有的权限
     */
    @OneToMany(fetch = FetchType.LAZY)
    private List<Permission> permissions;

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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
