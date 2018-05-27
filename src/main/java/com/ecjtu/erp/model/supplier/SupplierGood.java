package com.ecjtu.erp.model.supplier;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description: 供应商商品，真正的商品
 * @Date: Created in 0:26 2018/5/10
 * @Modified By:
 */
@Entity
@Table(name = "t_supplier_good")
public class SupplierGood implements Serializable {
    public static final Integer DEFAULT = 0;
    public static final Integer CAN = 1;
    public static final Integer CANT = 2;

    /**
     * 供应商商品编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierGoodId;

    /**
     * 由哪个新品生成
     */
    @Column
    private Long newGoodId;
    /**
     * 供应商编码
     */
    @Column
    @NotNull(message = "供应商编码不能为空")
    private Long supplierId;
    /**
     * 商品名称
     */
    @Column
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /**
     * 商品规格编号
     */
    @Column
    @NotNull(message = "商品规格编号不能为空")
    private Integer specificationId;

    /**
     * 商品SKU
     */
    @Column
    @NotNull(message = "商品SKU不能为空")
    private Integer skuId;

    /**
     * 品牌编号
     */
    @Column
    @NotNull(message = "品牌编号不能为空")
    private Integer brandId;

    /**
     * 商品类型
     */
    @Column
    @NotNull(message = "商品类型不能为空")
    private Integer type;

    /**
     * 商品标签
     */
    @Column
    @NotBlank(message = "商品标签不能为空")
    private String tag;

    /**
     * 采购价格
     */
    @NotNull(message = "采购价格不能为空")
    private Double buy;

    /**
     * 出售价格
     */
    @Column
    @NotNull(message = "出售价格不能为空")
    private Double sale;

    /**
     * 货币单位
     */
    @Column
    @NotNull(message = "货币单位不能为空")
    private Integer currencyUnitId;

    /**
     * 海关编码
     */
    @Column
    @NotNull(message = "海关编码不能为空")
    private Long customsCodeId;

    /**
     * 备注、描述
     */
    @Column
    private String description;

    /**
     * 商品重量
     */
    @Column
    @NotNull(message = "商品重量不能为空")
    private Double weight;

    /**
     * 商品体积
     */
    @Column
    @NotNull(message = "商品体积不能为空")
    private Double volume;

    /**
     * 材料构成
     */
    @Column
    @NotBlank(message = "材料构成不能为空")
    private String mater;

    /**
     * 商品特性
     */
    @Column
    @NotBlank(message = "商品特性不能为空")
    private String trait;

    /**
     * 状态
     */
    @Column
    private Integer status;

    /**
     * 当前库存
     */
    private Integer currentInventory;

    public Integer getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(Integer currentInventory) {
        this.currentInventory = currentInventory;
    }

    public Long getNewGoodId() {
        return newGoodId;
    }

    public void setNewGoodId(Long newGoodId) {
        this.newGoodId = newGoodId;
    }

    public Long getSupplierGoodId() {
        return supplierGoodId;
    }

    public void setSupplierGoodId(Long supplierGoodId) {
        this.supplierGoodId = supplierGoodId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    public Integer getCurrencyUnitId() {
        return currencyUnitId;
    }

    public void setCurrencyUnitId(Integer currencyUnitId) {
        this.currencyUnitId = currencyUnitId;
    }

    public Long getCustomsCodeId() {
        return customsCodeId;
    }

    public void setCustomsCodeId(Long customsCodeId) {
        this.customsCodeId = customsCodeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getMater() {
        return mater;
    }

    public void setMater(String mater) {
        this.mater = mater;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SupplierGood{" +
                "supplierGoodId=" + supplierGoodId +
                ", newGoodId=" + newGoodId +
                ", supplierId=" + supplierId +
                ", name='" + name + '\'' +
                ", specificationId=" + specificationId +
                ", skuId=" + skuId +
                ", brandId=" + brandId +
                ", type=" + type +
                ", tag='" + tag + '\'' +
                ", buy=" + buy +
                ", sale=" + sale +
                ", currencyUnitId=" + currencyUnitId +
                ", customsCodeId='" + customsCodeId + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", volume=" + volume +
                ", mater='" + mater + '\'' +
                ", trait='" + trait + '\'' +
                ", status=" + status +
                '}';
    }
}
