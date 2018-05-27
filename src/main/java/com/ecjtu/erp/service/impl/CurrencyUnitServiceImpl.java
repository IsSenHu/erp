package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.good.CurrencyUnit;
import com.ecjtu.erp.repository.CurrencyUnitRepository;
import com.ecjtu.erp.service.CurrencyUnitService;
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
 * @Author: HuSen
 * @Description:
 * @Date: Created in 13:36 2018/5/13
 * @Modified By:
 */
@Service
public class CurrencyUnitServiceImpl extends BaseServiceImpl implements CurrencyUnitService {
    private static final Logger log = LoggerFactory.getLogger(CurrencyUnitServiceImpl.class);
    @Autowired
    private CurrencyUnitRepository currencyUnitRepository;

    @Override
    public List<CurrencyUnit> findAll() {
        return currencyUnitRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveCurrencyUnit(CurrencyUnit currencyUnit) {
        if(Objects.isNull(currencyUnit)){
            return new JsonResult(500);
        }
        try {
            if(Objects.isNull(currencyUnit.getCurrencyUnitId())){
                log.info("新增货币单位:{}", currencyUnit);
                currencyUnitRepository.save(currencyUnit);
            }else {
                log.info("修改货币单位:{}", currencyUnit);
                CurrencyUnit old = currencyUnitRepository.findById(currencyUnit.getCurrencyUnitId()).get();
                BeanUtils.copyProperties(currencyUnit, old);
                currencyUnitRepository.save(old);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存货币单位失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public CurrencyUnit findCurrencyUnitById(Integer currencyUnitId) {
        return currencyUnitRepository.findById(currencyUnitId).get();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult deleteCurrencyUnitById(Integer currencyUnitId) {
        if(Objects.isNull(currencyUnitId)){
            return new JsonResult(400);
        }
        try {
            currencyUnitRepository.deleteById(currencyUnitId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除货币单位失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<CurrencyUnit> listCurrencyUnit() {
        return currencyUnitRepository.findAll();
    }
}
