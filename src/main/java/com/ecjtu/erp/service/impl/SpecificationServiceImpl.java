package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.good.Specification;
import com.ecjtu.erp.repository.SpecificationRepository;
import com.ecjtu.erp.service.SpecificationService;
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
 * @Date: Created in 8:39 2018/5/13
 * @Modified By:
 */
@Service
public class SpecificationServiceImpl extends BaseServiceImpl implements SpecificationService {
    private static final Logger log = LoggerFactory.getLogger(SpecificationServiceImpl.class);
    @Autowired
    private SpecificationRepository specificationRepository;

    @Override
    public List<Specification> findAll() {
        return specificationRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveSpecification(Specification specification) {
        if(Objects.isNull(specification)){
            return new JsonResult(500);
        }
        try {
            if(Objects.isNull(specification.getSpecificationId())){
                log.info("新增商品规格:{}", specification);
                specificationRepository.save(specification);
            }else {
                log.info("修改商品规格:{}", specification);
                Specification old = specificationRepository.findById(specification.getSpecificationId()).get();
                BeanUtils.copyProperties(specification, old);
                old.setSpecificationId(specification.getSpecificationId());
                specificationRepository.save(old);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存商品规格失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public Specification findSpecificationById(Integer specificationId) {
        return specificationRepository.findById(specificationId).get();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult deleteSpecificationById(Integer specificationId) {
        if(Objects.isNull(specificationId)){
            return new JsonResult(400);
        }
        try {
            specificationRepository.deleteById(specificationId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除规格失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
