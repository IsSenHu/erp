package com.ecjtu.erp.model.good;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 海关编码
 * @Date: Created in 11:14 2018/5/13
 * @Modified By:
 */
@Entity
@Table(name = "t_customs_code")
public class CustomsCode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotBlank(message = "名称规格不能为空")
    private String name;
    @Column
    @NotNull(message = "进口优惠税率不能为空")
    @DecimalMax(value = "100", message = "进口优惠税率在0-100之间")
    @DecimalMin(value = "0", message = "进口优惠税率在0-100之间")
    private Double importPreferentialTaxRate;
    @Column
    @NotNull(message = "进口普通税率不能为空")
    @DecimalMax(value = "100", message = "进口普通税率在0-100之间")
    @DecimalMin(value = "0", message = "进口普通税率在0-100之间")
    private Double importGeneralTaxRate;
    @Column
    @NotNull(message = "增值税率不能为空")
    @DecimalMax(value = "100", message = "增值税率在0-100之间")
    @DecimalMin(value = "0", message = "增值税率在0-100之间")
    private Double vAtRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomsCode() {
        super();
    }

    public Double getImportPreferentialTaxRate() {
        return importPreferentialTaxRate;
    }

    public void setImportPreferentialTaxRate(Double importPreferentialTaxRate) {
        this.importPreferentialTaxRate = importPreferentialTaxRate;
    }

    public Double getImportGeneralTaxRate() {
        return importGeneralTaxRate;
    }

    public void setImportGeneralTaxRate(Double importGeneralTaxRate) {
        this.importGeneralTaxRate = importGeneralTaxRate;
    }

    public Double getvAtRate() {
        return vAtRate;
    }

    public void setvAtRate(Double vAtRate) {
        this.vAtRate = vAtRate;
    }

    @Override
    public String toString() {
        return "CustomsCode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", importPreferentialTaxRate=" + importPreferentialTaxRate +
                ", importGeneralTaxRate=" + importGeneralTaxRate +
                ", vAtRate=" + vAtRate +
                '}';
    }
}
