package com.ecjtu.erp.model.vo.role;

import com.ecjtu.erp.model.vo.RoleVO;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 22:11 2018/5/2
 * @Modified By:
 */
public class EditRoleVo implements Serializable {
    /**
     * 已经拥有的角色
     */
    private List<RoleVO> have;

    /**
     * 未拥有的角色
     */
    private List<RoleVO> none;

    public List<RoleVO> getHave() {
        return have;
    }

    public void setHave(List<RoleVO> have) {
        this.have = have;
    }

    public List<RoleVO> getNone() {
        return none;
    }

    public void setNone(List<RoleVO> none) {
        this.none = none;
    }
}
