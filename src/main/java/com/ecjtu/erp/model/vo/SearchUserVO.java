package com.ecjtu.erp.model.vo;

import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description: 搜索用户的vo
 * @Date: Created in 16:57 2018/4/30
 * @Modified By:
 */
public class SearchUserVO implements Serializable {
    private String username;
    private String name;
    private String phone;
    private Integer currentPage;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "SearchUserVO{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
