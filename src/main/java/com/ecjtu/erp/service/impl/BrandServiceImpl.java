package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.good.Brand;
import com.ecjtu.erp.repository.BrandRepository;
import com.ecjtu.erp.service.BrandService;
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
 * @Date: Created in 10:29 2018/5/13
 * @Modified By:
 */
@Service
public class BrandServiceImpl extends BaseServiceImpl implements BrandService {
    private static final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveBrand(Brand brand) {
        if(Objects.isNull(brand)){
            return new JsonResult(500);
        }
        try {
            if(Objects.isNull(brand.getBrandId())){
                log.info("新增品牌:{}", brand);
                brandRepository.save(brand);
            }else {
                log.info("修改品牌:{}", brand);
                Brand old = brandRepository.findById(brand.getBrandId()).get();
                BeanUtils.copyProperties(brand, old);
                brandRepository.save(old);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存品牌失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public Brand findBrandById(Integer brandId) {
        return brandRepository.findById(brandId).get();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult deleteBrandById(Integer brandId) {
        if(Objects.isNull(brandId)){
            return new JsonResult(400);
        }
        try {
            brandRepository.deleteById(brandId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除品牌失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
