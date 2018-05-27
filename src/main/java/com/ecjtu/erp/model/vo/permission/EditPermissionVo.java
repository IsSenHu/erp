package com.ecjtu.erp.model.vo.permission;

import com.ecjtu.erp.model.vo.PermissionVO;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 20:25 2018/5/3
 * @Modified By:
 */
public class EditPermissionVo implements Serializable {
    private List<PermissionVO> have;
    private List<PermissionVO> none;

    public List<PermissionVO> getHave() {
        return have;
    }

    public void setHave(List<PermissionVO> have) {
        this.have = have;
    }

    public List<PermissionVO> getNone() {
        return none;
    }

    public void setNone(List<PermissionVO> none) {
        this.none = none;
    }
}
