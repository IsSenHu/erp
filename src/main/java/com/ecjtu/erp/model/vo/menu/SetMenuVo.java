package com.ecjtu.erp.model.vo.menu;

import com.ecjtu.erp.model.vo.MenuVO;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 22:36 2018/5/3
 * @Modified By:
 */
public class SetMenuVo implements Serializable {
    private MenuVO self;
    private List<MenuVO> enableSet;

    public MenuVO getSelf() {
        return self;
    }

    public void setSelf(MenuVO self) {
        this.self = self;
    }

    public List<MenuVO> getEnableSet() {
        return enableSet;
    }

    public void setEnableSet(List<MenuVO> enableSet) {
        this.enableSet = enableSet;
    }
}
