package com.ecjtu.erp.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description: 菜单
 * @Date: Created in 14:12 2018/4/30
 * @Modified By:
 */
@Entity
@Table(name = "t_menu")
public class Menu implements Serializable {

    /**
     * 是主菜单
     */
    public final static Integer MAJOR = 1;


    /**
     * 不是主菜单
     */
    public final static Integer CHILD = 2;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单地址
     */
    private String url;

    /**
     * 该菜单所属的权限
     */
    @OneToOne
    @JoinColumn(name = "permissionId", referencedColumnName = "permissionId")
    private Permission permission;

    /**
     * 是否为主菜单
     */
    @Column
    private Integer isMajor;

    /**
     * 父菜单的Id
     */
    @Column
    private Long fatherMenuId;

    /**
     * 图标
     */
    @Column
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getFatherMenuId() {
        return fatherMenuId;
    }

    public void setFatherMenuId(Long fatherMenuId) {
        this.fatherMenuId = fatherMenuId;
    }

    public Integer getIsMajor() {
        return isMajor;
    }

    public void setIsMajor(Integer isMajor) {
        this.isMajor = isMajor;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId=" + menuId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", permission=" + permission +
                '}';
    }
}
