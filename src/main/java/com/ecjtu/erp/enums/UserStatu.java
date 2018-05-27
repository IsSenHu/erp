package com.ecjtu.erp.enums;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 19:01 2018/4/30
 * @Modified By:
 */
public enum UserStatu {
    ENABLE(1, "启用"),
    DISABLED(2, "禁用");
    private Integer value;
    private String description;

    UserStatu(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
