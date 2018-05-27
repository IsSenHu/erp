package com.ecjtu.erp.util;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 胡森
 * @Description: 分页插件
 * @Date: Created in 17:00 2018/4/30
 * @Modified By:
 */
public class PageUtil<T> implements Serializable {
    private Integer currentPage;
    private Integer size;
    private Long totalRow;
    private Integer totalPage;
    private List<T> data;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageUtil{" +
                "currentPage=" + currentPage +
                ", size=" + size +
                ", totalRow=" + totalRow +
                ", totalPage=" + totalPage +
                '}';
    }
}
