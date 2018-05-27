package com.ecjtu.erp.model.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 5:09 2018/5/1
 * @Modified By:
 */
public class MenuVO implements Serializable {
    private Long menuId;
    @NotEmpty(message = "菜单名称不能为空")
    @Length(max = 10, message = "菜单名称的长度不能大于10")
    private String name;
    @NotEmpty(message = "菜单地址不能为空")
    @Length(max = 200, message = "菜单地址的长度不能大于200")
    private String url;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public MenuVO() {
        super();
    }

    public MenuVO(Long menuId, String name, String url, String icon) {
        super();
        this.menuId = menuId;
        this.name = name;
        this.url = url;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "MenuVO{" +
                "menuId=" + menuId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
