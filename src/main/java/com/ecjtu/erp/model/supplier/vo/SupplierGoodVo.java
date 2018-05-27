package com.ecjtu.erp.model.supplier.vo;

import com.ecjtu.erp.model.good.*;
import java.io.Serializable;

/**
 * @Author: 胡森
 * @Description: 供应商商品，真正的商品,vo
 * @Date: Created in 0:26 2018/5/10
 * @Modified By:
 */
public class SupplierGoodVo implements Serializable {
    private Long supplierGoodId;
    private Long newGoodId;
    private String name;
    private SupplierVo supplier;
    private Specification specification;
    private StockKeepingUnit stockKeepingUnit;
    private Brand brand;
    private Type type;
    private String tag;
    private Double buy;
    private Double sale;
    private CurrencyUnit currencyUnit;
    private CustomsCode customsCode;
    private String description;
    private Double weight;
    private Double volume;
    private String mater;
    private String trait;
    private Integer status;
    private Integer currentInventory;

    public Integer getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(Integer currentInventory) {
        this.currentInventory = currentInventory;
    }

    public Long getSupplierGoodId() {
        return supplierGoodId;
    }

    public void setSupplierGoodId(Long supplierGoodId) {
        this.supplierGoodId = supplierGoodId;
    }

    public Long getNewGoodId() {
        return newGoodId;
    }

    public void setNewGoodId(Long newGoodId) {
        this.newGoodId = newGoodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupplierVo getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierVo supplier) {
        this.supplier = supplier;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public StockKeepingUnit getStockKeepingUnit() {
        return stockKeepingUnit;
    }

    public void setStockKeepingUnit(StockKeepingUnit stockKeepingUnit) {
        this.stockKeepingUnit = stockKeepingUnit;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public CurrencyUnit getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(CurrencyUnit currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public CustomsCode getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(CustomsCode customsCode) {
        this.customsCode = customsCode;
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
}
