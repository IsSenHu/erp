package com.ecjtu.erp.service;

import com.ecjtu.erp.model.vo.MenuVO;
import com.ecjtu.erp.model.vo.ChildMenu;
import com.ecjtu.erp.model.vo.menu.SetMenuVo;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 4:57 2018/5/1
 * @Modified By:
 */
public interface MenuService extends BaseService {
    JsonResult saveMenu(MenuVO menuVO);

    JsonResult deleteMenu(Long menuId);

    MenuVO findMenuById(Long menuId);

    List<MenuVO> findByName(String name);

    List<MenuVO> menuVOS();

    JsonResult saveChild(ChildMenu childMenu);

    List<ChildMenu> childMenusByName(String name);

    ChildMenu findMenuByIdAndFindMajor(Long menuId);

    SetMenuVo findMenuByPermissionId(Long permissionId);
}
