package com.ecjtu.erp.util;

import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description: 接口返回的数据
 * @Date: Created in 16:05 2018/4/30
 * @Modified By:
 */
public class JsonResult<T> implements Serializable {
    private Integer code;
    private String description;
    private T data;

    public JsonResult(Integer code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    public JsonResult(Integer code, String description, T data) {
        super();
        this.code = code;
        this.description = description;
        this.data = data;
    }

    public JsonResult(Integer code) {
        super();
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}
