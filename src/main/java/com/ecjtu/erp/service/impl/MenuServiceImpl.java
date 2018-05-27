package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.Permission;
import com.ecjtu.erp.model.vo.MenuVO;
import com.ecjtu.erp.model.Menu;
import com.ecjtu.erp.model.vo.ChildMenu;
import com.ecjtu.erp.model.vo.menu.SetMenuVo;
import com.ecjtu.erp.repository.MenuRepository;
import com.ecjtu.erp.repository.PermissionRepository;
import com.ecjtu.erp.service.MenuService;
import com.ecjtu.erp.util.JsonResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 4:58 2018/5/1
 * @Modified By:
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {
    private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

    /**
     * 默认图标
     */
    private static final String ICON = "fa fa-table";

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    @Transactional
    public JsonResult saveMenu(MenuVO menuVO) {
        if(Objects.isNull(menuVO)){
            return new JsonResult(500);
        }
        Menu menu;
        try {
            if(Objects.isNull(menuVO.getMenuId())){
                log.info("添加菜单");
                menu = new Menu();
                menu.setName(menuVO.getName());
                menu.setUrl(menuVO.getUrl());
            }else {
                menu = menuRepository.findById(menuVO.getMenuId()).get();
                menu.setName(menuVO.getName());
                menu.setUrl(menuVO.getUrl());
                log.info("修改菜单");
            }
            //设为主菜单
            menu.setIsMajor(Menu.MAJOR);
            //设置图标
            menu.setIcon(StringUtils.isNotBlank(menuVO.getIcon()) ? menuVO.getIcon() : ICON);
            return Objects.isNull(menuRepository.save(menu)) ? new JsonResult(500) : new JsonResult(200);
        }catch (Exception e){
            log.error("保存失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional
    public JsonResult deleteMenu(Long menuId) {
        if(Objects.isNull(menuId)){
            return new JsonResult(400);
        }try {
            /**
             * 1,删除主菜单的时候，首先将所归属的权限的菜单置为空
             * 2,再将所拥有的子菜单的父菜单id置为空
             * */
            Menu menu = menuRepository.findById(menuId).get();
            if(!Objects.isNull(menu)){
                Permission permission = menu.getPermission();
                if(!Objects.isNull(permission)){
                    permission.setMenu(null);
                    permissionRepository.save(permission);
                }
            }
            List<Menu> childs = menuRepository.findAllByFatherMenuId(menuId);
            if(CollectionUtils.isNotEmpty(childs)){
                childs.stream().forEach(child -> {
                    child.setFatherMenuId(null);
                    menuRepository.save(child);
                });
            }
            menuRepository.deleteById(menuId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public MenuVO findMenuById(Long menuId) {
        if(Objects.isNull(menuId)){
            return new MenuVO();
        }else {
            Menu menu = menuRepository.findById(menuId).get();
            MenuVO menuVO = new MenuVO();
            menuVO.setMenuId(menuId);
            menuVO.setName(menu.getName());
            menuVO.setUrl(menu.getUrl());
            menuVO.setIcon(menu.getIcon());
            return menuVO;
        }
    }

    @Override
    public List<MenuVO> findByName(String name) {
        List<Menu> menus;
        if(StringUtils.isNotBlank(name)){
            menus = menuRepository.findAllByNameLikeAndIsMajor("%" + name + "%", Menu.MAJOR);
        }else {
            menus = menuRepository.findAllByIsMajor(Menu.MAJOR);
        }
        List<MenuVO> vos = new ArrayList<>();
        menus.stream().forEach(x -> {
            MenuVO menuVO = new MenuVO();
            menuVO.setMenuId(x.getMenuId());
            menuVO.setName(x.getName());
            menuVO.setUrl(x.getUrl());
            menuVO.setIcon(x.getIcon());
            vos.add(menuVO);
        });
        return vos;
    }

    @Override
    public List<MenuVO> menuVOS() {
        List<Menu> menus = menuRepository.findAllByIsMajor(Menu.MAJOR);
        List<MenuVO> vos = new ArrayList<>();
        menus.stream().forEach(x -> {
            MenuVO vo = new MenuVO();
            vo.setMenuId(x.getMenuId());
            vo.setName(x.getName());
            vos.add(vo);
        });
        return vos;
    }

    @Override
    @Transactional
    public JsonResult saveChild(ChildMenu childMenu) {
        if (Objects.isNull(childMenu)){
            return new JsonResult(500);
        }
        Menu menu;
        try {
            if(Objects.isNull(childMenu.getMenuId())){
                log.info("添加子菜单");
                menu = new Menu();
                menu.setName(childMenu.getName());
                menu.setUrl(childMenu.getUrl());
                menu.setIsMajor(Menu.CHILD);
                menu.setFatherMenuId(childMenu.getMajorMenuId());
            }else {
                log.info("修改子菜单");
                menu = menuRepository.findById(childMenu.getMenuId()).get();
                menu.setFatherMenuId(childMenu.getMajorMenuId());
                menu.setUrl(childMenu.getUrl());
                menu.setName(childMenu.getName());
            }
            return Objects.isNull(menuRepository.save(menu)) ? new JsonResult(500) : new JsonResult(200);
        }catch (Exception e){
            log.error("保存子菜单失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<ChildMenu> childMenusByName(String name) {
        List<Menu> menus;
        if(StringUtils.isNotBlank(name)){
            menus = menuRepository.findAllByNameLikeAndIsMajor("%" + name + "%", Menu.CHILD);
        }else {
            menus = menuRepository.findAllByIsMajor(Menu.CHILD);
        }
        List<ChildMenu> childMenus = new ArrayList<>();
        menus.stream().forEach(x -> {
            ChildMenu childMenu = new ChildMenu();
            childMenu.setMenuId(x.getMenuId());
            childMenu.setName(x.getName());
            childMenu.setUrl(x.getUrl());
            childMenu.setMajorMenuId(x.getFatherMenuId());
            childMenu.setMajorMenuName(Objects.isNull(x.getFatherMenuId()) ? null : menuRepository.findById(x.getFatherMenuId()).get().getName());
            childMenus.add(childMenu);
        });
        return childMenus;
    }

    @Override
    public ChildMenu findMenuByIdAndFindMajor(Long menuId) {
        if(Objects.isNull(menuId)){
            return new ChildMenu();
        }else {
            Menu menu = menuRepository.findById(menuId).get();
            ChildMenu childMenu = new ChildMenu();
            childMenu.setMenuId(menuId);
            childMenu.setMajorMenuId(menu.getFatherMenuId());
            childMenu.setName(menu.getName());
            childMenu.setUrl(menu.getUrl());
            List<Menu> menus = menuRepository.findAllByIsMajor(Menu.MAJOR);
            List<MenuVO> vos = new ArrayList<>();
            menus.stream().forEach(y -> {
                MenuVO menuVO = new MenuVO();
                menuVO.setMenuId(y.getMenuId());
                menuVO.setName(y.getName());
                menuVO.setUrl(y.getUrl());
                vos.add(menuVO);
            });
            childMenu.setMajors(vos);
            return childMenu;
        }
    }

    @Override
    public SetMenuVo findMenuByPermissionId(Long permissionId) {
        if(Objects.isNull(permissionId)){
            log.info("权限的ID为空，错误");
            return null;
        }
        Menu menu = menuRepository.findByPermissionPermissionId(permissionId);
        SetMenuVo setMenuVo = new SetMenuVo();
        setMenuVo.setSelf(Objects.isNull(menu) ? null : new MenuVO(menu.getMenuId(), menu.getName(), menu.getUrl(), menu.getIcon()));
        List<Menu> menus = menuRepository.findAll();
        //过滤出可设置的菜单 菜单还必须为主菜单
        List<Menu> enableSet = menus.stream()
                .filter(x -> Objects.isNull(x.getPermission()) && Objects.isNull(x.getFatherMenuId())).collect(Collectors.toList());
        log.info("过滤出可设置的菜单数量为:{}", enableSet.size());
        List<MenuVO> enableAddVo = enableSet.stream()
                .map(enable -> new MenuVO(enable.getMenuId(), enable.getName(), enable.getUrl(), enable.getIcon())).collect(Collectors.toList());
        setMenuVo.setEnableSet(enableAddVo);
        return setMenuVo;
    }
}
