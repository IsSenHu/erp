package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.enums.UserStatu;
import com.ecjtu.erp.model.*;
import com.ecjtu.erp.model.billdetail.BillDetail;
import com.ecjtu.erp.model.vo.Index;
import com.ecjtu.erp.model.vo.UserVO;
import com.ecjtu.erp.repository.*;
import com.ecjtu.erp.service.UserService;
import com.ecjtu.erp.tran.UserVoToPo;
import com.ecjtu.erp.util.JsonResult;
import com.ecjtu.erp.util.PageUtil;
import com.ecjtu.erp.model.vo.SearchUserVO;
import com.ecjtu.erp.repository.specification.UserSpecification;
import com.ecjtu.erp.tran.UserPoToVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 16:09 2018/4/30
 * @Modified By:
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService, UserDetailsService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final static String USERNAME_NOT_EXIST = "用户名不存在";

    @Autowired
    private UserVoToPo userVoToPo;

    @Autowired
    private UserPoToVo userPoToVo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SupplierGoodRepository supplierGoodRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveUser(UserVO userVO) {
        if(Objects.isNull(userVO)){
            log.error("参数异常:{}", userVO);
            return new JsonResult(500, "参数异常");
        }
        if(Objects.isNull(userVO.getUserId())){
            try {
                log.info("添加用户");
                User user = userVoToPo.apply(userVO);
                User save = userRepository.save(user);
                log.info("添加的用户为；{}", save);
                return Objects.isNull(save) ? new JsonResult(500) : new JsonResult(200);
            }catch (Exception e){
                log.error("发生异常，添加失败:{}", e.getMessage());
                e.printStackTrace();
                return new JsonResult(500);
            }
        }else {
           try {
               log.info("更新用户信息");
               User find = userRepository.findById(userVO.getUserId()).get();
               find.setUsername(StringUtils.isBlank(userVO.getUsername()) ? find.getUsername() : userVO.getUsername());
               find.setPassword(StringUtils.isBlank(userVO.getPassword()) ? find.getPassword() : userVO.getPassword());
               find.setName(userVO.getName());
               find.setPhone(userVO.getPhone());
               find.setEmail(userVO.getEmail());
               User save = userRepository.save(find);
               log.info("更新的用户为:{}", save);
               return Objects.isNull(save) ? new JsonResult(500) : new JsonResult(200);
           }catch (Exception e){
               log.error("发生异常，更新失败:{}", e.getMessage());
               e.printStackTrace();
               return new JsonResult(500);
           }
        }
    }

    @Override
    public PageUtil<UserVO> searchUser(SearchUserVO searchUserVO) {
        log.info("查询用户:{}", searchUserVO);
        Pageable pageable = new PageRequest(Objects.isNull(searchUserVO.getCurrentPage()) ? 0 : searchUserVO.getCurrentPage() - 1, (int) userRepository.count());
        Page<User> page = userRepository.findAll(new UserSpecification(searchUserVO), pageable);
        PageUtil<UserVO> pageUtil = new PageUtil<>();
        pageUtil.setCurrentPage(page.getNumber() + 1);
        pageUtil.setSize(page.getSize());
        pageUtil.setTotalPage(page.getTotalPages());
        pageUtil.setTotalRow(page.getTotalElements());
        List<UserVO> data = new ArrayList<>();
        page.getContent().stream().forEach(x -> {
            UserVO vo = userPoToVo.apply(x);
            data.add(vo);
        });
        pageUtil.setData(data);
        return pageUtil;
    }

    @Override
    @Transactional
    public JsonResult deleteUser(Long userId) {
        if(Objects.isNull(userId)){
            log.info("用户ID不能为空:{}", userId);
            return new JsonResult(400);
        }
        try {
            //删除用户的时候 把属于该用户的用户角色权限也删除了
            userRoleRepository.deleteByUserId(userId);
            userRepository.deleteById(userId);
            log.info("删除成功:{}", userId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional
    public JsonResult changeUserStatus(Long userId) {
        if(Objects.isNull(userId)){
            return new JsonResult(400);
        }
        try {
            User user = userRepository.findById(userId).get();
            user.setStatu(user.getStatu() == UserStatu.ENABLE.getValue() ? UserStatu.DISABLED.getValue() : UserStatu.ENABLE.getValue());
            userRepository.save(user);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("修改用户状态失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional
    public JsonResult addUserRoles(Long userId, String roleIdStr) {
        if(Objects.isNull(userId)) {
            return new JsonResult(403);
        }
        if(StringUtils.isBlank(roleIdStr)){
            return new JsonResult(400);
        }else {
            try {
                String[] roleIdArray = roleIdStr.split(",");
                List<Integer> roleIdList = Arrays.stream(roleIdArray).map(id -> Integer.valueOf(id)).collect(Collectors.toList());
                //该用户必须没有这个角色才能添加
                List<Integer> have = userRoleRepository.findAllByUserId(userId).stream()
                        .map(userRole -> userRole.getRoleId()).collect(Collectors.toList());
                //用set去重
                Set<Integer> roleIdSet = roleIdList.stream().filter(id -> !have.contains(id)).collect(Collectors.toSet());

                //开始添加
                List<UserRole> userRoleList = roleIdSet.stream()
                        .map(roleId -> new UserRole(userId, roleId)).collect(Collectors.toList());
                userRoleRepository.saveAll(userRoleList);
                return new JsonResult(200);
            }catch (Exception e){
                log.error("添加用的角色失败，发生异常:{}", e.getMessage());
                e.printStackTrace();
                return new JsonResult(500);
            }
        }
    }

    @Override
    @Transactional
    public JsonResult deleteUserRoles(Long userId, String roleIdStr) {
        if(Objects.isNull(userId)){
            return new JsonResult(403);
        }
        if(StringUtils.isBlank(roleIdStr)){
            return new JsonResult(400);
        }
        try {
            String[] roleIdArray = roleIdStr.split(",");
            List<Integer> roleIdList = Arrays.stream(roleIdArray).map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            //校验用户是否具有这些关系
            List<UserRole> userRoles = userRoleRepository.findAllByUserId(userId);
            List<Integer> have = userRoles.stream()
                    .map(userRole -> userRole.getRoleId()).collect(Collectors.toList());
            //用set去重
            List<UserRole> delete = roleIdList.stream().filter(roleId -> have.contains(roleId)).collect(Collectors.toSet())
                    .stream().map(roleId -> userRoleRepository.findByUserIdAndRoleId(userId, roleId)).collect(Collectors.toList());
            log.info("要删除的用户角色数量为:{}", delete.size());
            //开始删除
            userRoleRepository.deleteAll(delete);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除用户的角色失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    /**
     * 重写loadUserByUsername方法获得userDetail类型用户
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:{}", username);
        User user = userRepository.findByUsername(username);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException(USERNAME_NOT_EXIST);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        /**
         * 获得该用户的所有权限，权限的最小细粒度是每个菜单的地址。
         * 也就是url.
         * */
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(user.getUserId());
        if(CollectionUtils.isNotEmpty(userRoles)){
            List<Role> roles = userRoles.stream().map(userRole -> roleRepository.findById(userRole.getRoleId()).get()).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(roles)){
                roles.stream().forEach(role -> permissionRepository.findAllByRoleRoleId(role.getRoleId())
                .stream().forEach(permission -> {
                    if(!Objects.isNull(permission) && !Objects.isNull(permission.getMenu())){
                        authorities.add(new SimpleGrantedAuthority(permission.getMenu().getUrl()));
                        menuRepository.findAllByFatherMenuId(permission.getMenu().getMenuId())
                                .stream().forEach(menu -> authorities.add(new SimpleGrantedAuthority(menu.getUrl())));
                    }}));
            }
        }
        log.info("用户的总共拥有资源权限数量为:{}", authorities.size());
        user.setAuthorities(authorities);
        return user;
    }

    /**
     * 获取所有的url资源
     * @return
     */
    @Override
    public List<String> allUrls() {
        List<String> allUrls = new ArrayList<>();
        List<Menu> menus = menuRepository.findAll();
        for(Menu menu : menus){
            allUrls.add(menu.getUrl());
        }
        return allUrls;
    }

    @Override
    public Index index() {
        Index index = new Index();
        index.setTypeNumber(supplierGoodRepository.count());
        index.setCustomerNumber(customerRepository.count());
        index.setSupplierNumber(supplierRepository.count());
        Pageable pageable = new PageRequest(0, 1, new Sort(new Sort.Order(Sort.Direction.DESC, "occurTime")));
        Page<BillDetail> page = billDetailRepository.findAll(pageable);
        if(CollectionUtils.isNotEmpty(page.getContent())){
            index.setMoney(page.getContent().get(0).getNowMoney());
        }else {
            index.setMoney(0.00);
        }
        return index;
    }
}
