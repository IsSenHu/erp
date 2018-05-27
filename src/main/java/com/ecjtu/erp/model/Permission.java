package com.ecjtu.erp.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description:    权限
 * @Date: Created in 14:10 2018/4/30
 * @Modified By:
 */
@Entity
@Table(name = "t_permission")
public class Permission implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    /**
     * 权限名称
     */
    @Column
    private String name;

    /**
     * 权限描述
     */
    @Column
    private String description;

    /**
     * 该权限的菜单
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menuId", referencedColumnName = "menuId")
    private Menu menu;

    /**
     * 该权限所属的角色
     */
    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
