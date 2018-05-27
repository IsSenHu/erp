package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.vo.MenuVO;
import com.ecjtu.erp.service.MenuService;
import com.ecjtu.erp.util.JsonResult;
import com.ecjtu.erp.model.vo.ChildMenu;
import com.ecjtu.erp.model.vo.menu.SetMenuVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 4:57 2018/5/1
 * @Modified By:
 */
@RestController
public class MenuController {
    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 主菜单页面
     * @param name
     * @return
     */
    @GetMapping("/menus")
    private ModelAndView menus(String name){
        List<MenuVO> menuVOS = menuService.findByName(name);
        return new ModelAndView("pages/tables/menus")
                .addObject("menus", menuVOS)
                .addObject("menusShow", menuService.menusAndChilds());
    }

    /**
     * 修改或添加菜单
     * @param menuVO
     * @param result
     * @return
     */
    @PostMapping("/saveMenu")
    private JsonResult saveMenu(@Valid @RequestBody MenuVO menuVO, BindingResult result){
        if(result.hasErrors()){
            log.info("输入有误");
            return new JsonResult(400, result.getFieldErrors());
        }
        return menuService.saveMenu(menuVO);
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @PostMapping("/deleteMenu")
    private JsonResult deleteMenu(Long menuId){
        return menuService.deleteMenu(menuId);
    }

    /**
     * 根据Id查询菜单
     * @param menuId
     * @return
     */
    @PostMapping("/findMenuById")
    private MenuVO findMenuById(Long menuId){
        return menuService.findMenuById(menuId);
    }

    /**
     * 子菜单页面
     * @param name
     * @return
     */
    @GetMapping("/childMenus")
    private ModelAndView childMenus(String name){
        List<ChildMenu> childMenus = menuService.childMenusByName(name);
        return new ModelAndView("pages/tables/childMenus")
                .addObject("menus", childMenus)
                .addObject("menusShow", menuService.menusAndChilds());
    }

    /**
     * 查询所有的主菜单
     * @return
     */
    @PostMapping("/menuVOS")
    private List<MenuVO> menuVOS(){
        return menuService.menuVOS();
    }

    /**
     * 保存子菜单
     * @param childMenu
     * @param result
     * @return
     */
    @PostMapping("/saveChild")
    private JsonResult saveChild(@RequestBody @Valid ChildMenu childMenu, BindingResult result){
        if(result.hasErrors()){
            log.info("输入有误");
            return new JsonResult(400, result.getFieldErrors());
        }
        return menuService.saveChild(childMenu);
    }

    /**
     * 根据ID查询菜单信息和所有的主菜单信息
     * @param menuId
     * @return
     */
    @PostMapping("/findMenuByIdAndFindMajor")
    private ChildMenu findMenuByIdAndFindMajor(Long menuId){
        return menuService.findMenuByIdAndFindMajor(menuId);
    }

    /**
     * 根据权限查找主菜单
     * @param permissionId
     * @return
     */
    @PostMapping("/findMenuByPermissionId")
    private SetMenuVo findMenuByPermissionId(Long permissionId){
        return menuService.findMenuByPermissionId(permissionId);
    }
}
