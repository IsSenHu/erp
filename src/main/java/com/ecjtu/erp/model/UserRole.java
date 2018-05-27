package com.ecjtu.erp.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description: 用户角色关系
 * @Date: Created in 18:45 2018/4/30
 * @Modified By:
 */
@Entity
@Table(name = "t_user_role")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column
    private Integer roleId;

    public UserRole() {
    }

    public UserRole(Long userId, Integer roleId) {
        super();
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
