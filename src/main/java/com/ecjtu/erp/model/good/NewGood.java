package com.ecjtu.erp.model.good;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Author: 胡森
 * @Description: 物料/商品的实体类
 * @Date: Created in 21:12 2018/5/6
 * @Modified By:
 */
@Entity
@Table(name = "t_new_good")
public class NewGood implements Serializable {
    /**
     * 初始状态
     */
    public static final Integer DEFAULT = 0;
    /**
     * 待判断
     */
    public static final Integer NODO = 1;
    /**
     * 待审核
     */
    public static final Integer WAIT = 2;

    /**
     * 有效新品
     */
    public static final Integer YES = 3;

    /**
     * 无效新品
     */
    public static final Integer ERROR = 4;

    /**
     * 可开发
     */
    public static final Integer CAN_PRODUCT = 5;

    /**
     * 不可开发
     */
    public static final Integer CANT_PRODUCT = 6;

    /**
     * 与供应商已绑定
     */
    public static final Integer BING_SUPPLIER=7;

    /**
     * 与供应商连接中
     */
    public static final Integer CONNECTING_SUPPLIER = 8;

    /**
     * 无法与供应商连接
     */
    public static final Integer CANT_CONNECTING_SUPPLIER = 9;
    /**
     * 天猫淘宝
     */
    public static final Integer TIAN_MAO_TAO_BAO = 11;

    /**
     * 京东
     */
    public static final Integer JING_DONG = 12;

    /**
     * 人工添加
     */
    public static final Integer PEOPLE = 13;

    /**
     * 其它
     */
    public static final Integer ANOTHER = 14;

    /**
     * 物料/商品编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;

    /**
     * 物料/商品的名称
     */
    @Column
    @NotEmpty(message = "商品的名称不能为空")
    private String name;

    /**
     * 商品来源方式
     */
    @Column
    @NotNull(message = "商品来源方式不能为空")
    private Integer fromType;

    /**
     * 销售量
     */
    @Column
    @NotNull(message = "销售量不能为空")
    private Long saleNumber;

    /**
     * 商品来源
     */
    @Column
    @NotEmpty(message = "商品来源不能为空")
    private String fromWhere;

    /**
     * 商品来源店铺的地址
     */
    @Column
    @NotEmpty(message = "商品来源店铺的地址不能为空")
    private String address;

    /**
     * 商品来源的店铺
     */
    @Column
    private String shop;

    /**
     * 现在的价格
     */
    @Column
    private Double nowPrice;

    /**
     * 成本价
     */
    @Column
    @NotNull(message = "成本价不能为空")
    private Double originPrice;

    /**
     * 上市时间
     */
    @Column
    private LocalDate up2MarketDate;

    /**
     * 新品状态
     */
    @Column
    private Integer status;

    /**
     * 其它描述
     */
    @Column
    private String description;

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public Long getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Long saleNumber) {
        this.saleNumber = saleNumber;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public LocalDate getUp2MarketDate() {
        return up2MarketDate;
    }

    public void setUp2MarketDate(LocalDate up2MarketDate) {
        this.up2MarketDate = up2MarketDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
