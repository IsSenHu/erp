package com.ecjtu.erp.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 22:00 2018/5/1
 * @Modified By:
 */
public class UserRolesAndAllRoles implements Serializable {
    private List<RoleVO> userRoles;
    private List<RoleVO> otherRoles;

    public List<RoleVO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<RoleVO> userRoles) {
        this.userRoles = userRoles;
    }

    public List<RoleVO> getOtherRoles() {
        return otherRoles;
    }

    public void setOtherRoles(List<RoleVO> otherRoles) {
        this.otherRoles = otherRoles;
    }
}
