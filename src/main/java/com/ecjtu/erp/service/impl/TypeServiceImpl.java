package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.good.Type;
import com.ecjtu.erp.repository.TypeRepository;
import com.ecjtu.erp.service.TypeService;
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
 * @Date: Created in 13:14 2018/5/13
 * @Modified By:
 */
@Service
public class TypeServiceImpl extends BaseServiceImpl implements TypeService {
    private static final Logger log = LoggerFactory.getLogger(TypeServiceImpl.class);
    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveType(Type type) {
        if(Objects.isNull(type)){
            return new JsonResult(500);
        }
        try {
            if(Objects.isNull(type.getTypeId())){
                log.info("新增商品类型:{}", type);
                typeRepository.save(type);
            }else {
                log.info("修改商品类型:{}", type);
                Type old = typeRepository.findById(type.getTypeId()).get();
                BeanUtils.copyProperties(type, old);
                typeRepository.save(old);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存商品类型失败，发生异常:{}", e.getMessage());
            e.printStackTrace();;
            return new JsonResult(500);
        }
    }

    @Override
    public Type findTypeById(Integer typeId) {
        return typeRepository.findById(typeId).get();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult deleteTypeById(Integer typeId) {
        if (Objects.isNull(typeId)) {
            return new JsonResult(400);
        }
        try {
            typeRepository.deleteById(typeId);
            return new JsonResult(200);
        } catch (Exception e) {
            log.error("删除商品类型失败。发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
