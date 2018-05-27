package com.ecjtu.erp.model.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 19:51 2018/5/1
 * @Modified By:
 */
public class ChildMenu extends MenuVO implements Serializable {
    @NotNull(message = "主菜单不能为空")
    private Long majorMenuId;
    private String majorMenuName;
    private List<MenuVO> majors;

    public List<MenuVO> getMajors() {
        return majors;
    }

    public void setMajors(List<MenuVO> majors) {
        this.majors = majors;
    }

    public Long getMajorMenuId() {
        return majorMenuId;
    }

    public void setMajorMenuId(Long majorMenuId) {
        this.majorMenuId = majorMenuId;
    }

    public String getMajorMenuName() {
        return majorMenuName;
    }

    public void setMajorMenuName(String majorMenuName) {
        this.majorMenuName = majorMenuName;
    }

    @Override
    public String toString() {
        return "ChildMenu{" +
                "majorMenuId=" + majorMenuId +
                ", majorMenuName='" + majorMenuName + '\'' +
                '}';
    }
}
