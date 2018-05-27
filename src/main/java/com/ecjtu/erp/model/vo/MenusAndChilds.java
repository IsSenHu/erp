package com.ecjtu.erp.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 20:41 2018/5/1
 * @Modified By:
 */
public class MenusAndChilds implements Serializable {
    private MenuVO major;

    private List<MenuVO> childs;

    public MenuVO getMajor() {
        return major;
    }

    public void setMajor(MenuVO major) {
        this.major = major;
    }

    public List<MenuVO> getChilds() {
        return childs;
    }

    public void setChilds(List<MenuVO> childs) {
        this.childs = childs;
    }
}
