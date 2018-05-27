package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.good.StockKeepingUnit;
import com.ecjtu.erp.repository.StockKeepingUnitRepository;
import com.ecjtu.erp.service.StockKeepingUnitService;
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
 * @Date: Created in 9:55 2018/5/13
 * @Modified By:
 */
@Service
public class StockKeepingUnitServiceImpl extends BaseServiceImpl implements StockKeepingUnitService {
    private static final Logger log = LoggerFactory.getLogger(StockKeepingUnitServiceImpl.class);

    @Autowired
    private StockKeepingUnitRepository stockKeepingUnitRepository;

    @Override
    public List<StockKeepingUnit> findAll() {
        return stockKeepingUnitRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveStockKeepUnit(StockKeepingUnit stockKeepingUnit) {
        if(Objects.isNull(stockKeepingUnit)){
            return new JsonResult(500);
        }
        try{
            if(Objects.isNull(stockKeepingUnit.getUnitId())){
                log.info("新增sku;:{}", stockKeepingUnit);
                stockKeepingUnitRepository.save(stockKeepingUnit);
            }else {
                log.info("修改sku:{}", stockKeepingUnit);
                StockKeepingUnit old = stockKeepingUnitRepository.findById(stockKeepingUnit.getUnitId()).get();
                BeanUtils.copyProperties(stockKeepingUnit, old);
                stockKeepingUnitRepository.save(old);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存sku失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public StockKeepingUnit findStockKeepUnitById(Integer unitId) {
        return stockKeepingUnitRepository.findById(unitId).get();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult deleteStockKeepUnitById(Integer unitId) {
        if(Objects.isNull(unitId)){
            return new JsonResult(400);
        }
        try {
            stockKeepingUnitRepository.deleteById(unitId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除sku失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
