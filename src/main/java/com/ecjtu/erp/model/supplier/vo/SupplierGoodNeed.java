package com.ecjtu.erp.model.supplier.vo;

import com.ecjtu.erp.model.good.*;
import com.ecjtu.erp.model.supplier.Supplier;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 15:51 2018/5/13
 * @Modified By:
 */
public class SupplierGoodNeed implements Serializable {
    private List<Supplier> suppliers;
    private List<CustomsCode> customsCodes;
    private List<Specification> specifications;
    private List<StockKeepingUnit> stockKeepingUnits;
    private List<Brand> brands;
    private List<CurrencyUnit> currencyUnits;
    private List<Type> types;

    public SupplierGoodNeed() {
        super();
    }

    public SupplierGoodNeed(List<Supplier> suppliers, List<CustomsCode> customsCodes, List<Specification> specifications, List<StockKeepingUnit> stockKeepingUnits, List<Brand> brands, List<CurrencyUnit> currencyUnits, List<Type> types) {
        super();
        this.suppliers = suppliers;
        this.customsCodes = customsCodes;
        this.specifications = specifications;
        this.stockKeepingUnits = stockKeepingUnits;
        this.brands = brands;
        this.currencyUnits = currencyUnits;
        this.types = types;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<CustomsCode> getCustomsCodes() {
        return customsCodes;
    }

    public void setCustomsCodes(List<CustomsCode> customsCodes) {
        this.customsCodes = customsCodes;
    }

    public List<Specification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<Specification> specifications) {
        this.specifications = specifications;
    }

    public List<StockKeepingUnit> getStockKeepingUnits() {
        return stockKeepingUnits;
    }

    public void setStockKeepingUnits(List<StockKeepingUnit> stockKeepingUnits) {
        this.stockKeepingUnits = stockKeepingUnits;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<CurrencyUnit> getCurrencyUnits() {
        return currencyUnits;
    }

    public void setCurrencyUnits(List<CurrencyUnit> currencyUnits) {
        this.currencyUnits = currencyUnits;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
}
