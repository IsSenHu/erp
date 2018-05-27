package com.ecjtu.erp.model.vo.contacts;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 18:18 2018/5/6
 * @Modified By:
 */
public class ReturnVo<T, R> {
    private R data;

    private List<T> list;

    public R getData() {
        return data;
    }

    public void setData(R data) {
        this.data = data;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
