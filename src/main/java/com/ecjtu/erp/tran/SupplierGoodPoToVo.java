package com.ecjtu.erp.tran;

import com.ecjtu.erp.model.good.*;
import com.ecjtu.erp.model.supplier.Supplier;
import com.ecjtu.erp.model.supplier.SupplierGood;
import com.ecjtu.erp.model.supplier.vo.SupplierGoodVo;
import com.ecjtu.erp.model.supplier.vo.SupplierVo;
import com.ecjtu.erp.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 16:20 2018/5/12
 * @Modified By:
 */
@Component
public class SupplierGoodPoToVo implements Function<SupplierGood, SupplierGoodVo> {

    @Autowired
    private SupplierPoToVo supplierPoToVo;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CurrencyUnitRepository currencyUnitRepository;

    @Autowired
    private CustomsCodeRepository customsCodeRepository;

    @Autowired
    private SpecificationRepository specificationRepository;

    @Autowired
    private StockKeepingUnitRepository stockKeepingUnitRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public SupplierGoodVo apply(SupplierGood supplierGood) {
        SupplierGoodVo vo = new SupplierGoodVo();
        if(!Objects.isNull(supplierGood)){
            BeanUtils.copyProperties(supplierGood, vo);
            Brand brand = brandRepository.findById(supplierGood.getBrandId()).get();
            if(!Objects.isNull(brand)){
                vo.setBrand(brand);
            }
            CurrencyUnit currencyUnit = currencyUnitRepository.findById(supplierGood.getCurrencyUnitId()).get();
            if(!Objects.isNull(currencyUnit)){
                vo.setCurrencyUnit(currencyUnit);
            }
            CustomsCode customsCode = customsCodeRepository.findById(supplierGood.getCustomsCodeId()).get();
            if(!Objects.isNull(customsCode)){
                vo.setCustomsCode(customsCode);
            }
            Specification specification = specificationRepository.findById(supplierGood.getSpecificationId()).get();
            if(!Objects.isNull(specification)){
                vo.setSpecification(specification);
            }
            StockKeepingUnit stockKeepingUnit = stockKeepingUnitRepository.findById(supplierGood.getSkuId()).get();
            if(!Objects.isNull(stockKeepingUnit)){
                vo.setStockKeepingUnit(stockKeepingUnit);
            }
            Supplier supplier = supplierRepository.findById(supplierGood.getSupplierId()).get();
            if(!Objects.isNull(supplier)){
                SupplierVo supplierVo = supplierPoToVo.apply(supplier);
                vo.setSupplier(supplierVo);
            }
            Type type = typeRepository.findById(supplierGood.getType()).get();
            if(!Objects.isNull(type)){
                vo.setType(type);
            }
        }
        return vo;
    }
}
