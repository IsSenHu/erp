package com.ecjtu.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author: 胡森
 * @Description: 用户
 * @Date: Created in 14:05 2018/4/30
 * @Modified By:
 */
@Entity
@Table(name = "t_user")
public class User implements UserDetails, Serializable{

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * 用户名
     */
    @Column
    private String username;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     * 用户的姓名
     */
    @Column
    private String name;
    /**
     * 手机号
     */
    @Column
    private String phone;

    /**
     * 邮箱
     */
    @Column
    private String email;

    /**
     * 用户状态
     */
    @Column
    private Integer statu;

    @Transient
    private List<? extends GrantedAuthority> authorities;

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(List<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * 账户是否不过期
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否不锁定
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 证书是否不过期
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 已启用
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
