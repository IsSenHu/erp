package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.good.*;
import com.ecjtu.erp.model.supplier.Supplier;
import com.ecjtu.erp.model.supplier.SupplierGood;
import com.ecjtu.erp.model.supplier.vo.SupplierGoodNeed;
import com.ecjtu.erp.model.supplier.vo.SupplierGoodVo;
import com.ecjtu.erp.repository.*;
import com.ecjtu.erp.repository.specification.SupplierGoodSpecification;
import com.ecjtu.erp.service.SupplierGoodService;
import com.ecjtu.erp.tran.SupplierGoodPoToVo;
import com.ecjtu.erp.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 16:01 2018/5/12
 * @Modified By:
 */
@Service
public class SupplierGoodServiceImpl extends BaseServiceImpl implements SupplierGoodService {
    private static final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    private SupplierGoodRepository supplierGoodRepository;

    @Autowired
    private NewGoodRepository newGoodRepository;

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

    @Autowired
    private SupplierGoodPoToVo supplierGoodPoToVo;

    @Override
    public NewGood findNewGoodById(Long newGoodId) {
        return newGoodRepository.findById(newGoodId).get();
    }

    @Override
    public SupplierGoodNeed getAllCreateNeed() {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<CurrencyUnit> currencyUnits = currencyUnitRepository.findAll();
        List<CustomsCode> customsCodes = customsCodeRepository.findAll();
        List<Specification> specifications = specificationRepository.findAll();
        List<StockKeepingUnit> stockKeepingUnits = stockKeepingUnitRepository.findAll();
        List<Brand> brands = brandRepository.findAll();
        List<Type> types = typeRepository.findAll();
        return new SupplierGoodNeed(suppliers, customsCodes, specifications, stockKeepingUnits, brands, currencyUnits, types);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveSupplierGood(SupplierGood supplierGood) {
        if(Objects.isNull(supplierGood)){
            return new JsonResult(500);
        }
        try {
            if(Objects.isNull(supplierGood.getSupplierGoodId())){
                log.info("新增供应商商品:{}", supplierGood);
                supplierGood.setStatus(SupplierGood.DEFAULT);
                NewGood newGood = newGoodRepository.findById(supplierGood.getNewGoodId()).get();
                newGood.setStatus(NewGood.CONNECTING_SUPPLIER);
                supplierGood.setCurrentInventory(0);
                newGoodRepository.save(newGood);
                supplierGoodRepository.save(supplierGood);
            }else {
                log.info("修改供应商商品:{}", supplierGood);
                SupplierGood old = supplierGoodRepository.findById(supplierGood.getSupplierId()).get();
                Integer status = old.getStatus();
                BeanUtils.copyProperties(supplierGood, old);
                old.setStatus(status);
                supplierGoodRepository.save(old);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存供应商商品失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<SupplierGood> findAll(SupplierGood supplierGood) {
        return supplierGoodRepository.findAll(new SupplierGoodSpecification(supplierGood));
    }

    @Override
    public SupplierGoodVo findSupplierGoodById(Long supplierGoodId) {
        log.info("supplierGoodId:{}", supplierGoodId);
        return supplierGoodPoToVo.apply(supplierGoodRepository.findById(supplierGoodId).get());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult canOrNotCanSupply(Long supplierGoodId, Integer flag) {
        if(Objects.isNull(supplierGoodId) || Objects.isNull(flag)){
            return new JsonResult(400);
        }
        try {
            SupplierGood supplierGood = supplierGoodRepository.findById(supplierGoodId).get();
            NewGood newGood = newGoodRepository.findById(supplierGood.getNewGoodId()).get();
            if(!SupplierGood.DEFAULT.equals(supplierGood.getStatus()) && !SupplierGood.DEFAULT.equals(flag)){
                return new JsonResult(400);
            }
            if(SupplierGood.CAN.equals(flag)){
                supplierGood.setStatus(SupplierGood.CAN);
                newGood.setStatus(NewGood.BING_SUPPLIER);
            }else if (SupplierGood.CANT.equals(flag)){
                supplierGood.setStatus(SupplierGood.CANT);
                newGood.setStatus(NewGood.CANT_CONNECTING_SUPPLIER);
            }else if(SupplierGood.DEFAULT.equals(flag)){
                supplierGood.setStatus(SupplierGood.DEFAULT);
            }
            supplierGoodRepository.save(supplierGood);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("连接供应商和商品失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<SupplierGood> supplierGoodList() {
        return supplierGoodRepository.findAllByStatus(SupplierGood.CAN);
    }

    @Override
    public SupplierGood findSupplierGoodById2(Long supplierGoodId) {
        return supplierGoodRepository.findById(supplierGoodId).get();
    }
}
