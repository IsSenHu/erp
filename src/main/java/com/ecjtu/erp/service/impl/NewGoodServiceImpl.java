package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.good.NewGood;
import com.ecjtu.erp.repository.NewGoodRepository;
import com.ecjtu.erp.repository.specification.NewGoodSpecification;
import com.ecjtu.erp.service.NewGoodService;
import com.ecjtu.erp.util.JsonResult;
import org.apache.commons.lang3.StringUtils;
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
 * @Date: Created in 21:33 2018/5/6
 * @Modified By:
 */
@Service
public class NewGoodServiceImpl extends BaseServiceImpl implements NewGoodService {
    private static final Logger log = LoggerFactory.getLogger(NewGoodServiceImpl.class);

    @Autowired
    private NewGoodRepository newGoodRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveNewGood(NewGood newGood) {
        if(Objects.isNull(newGood)){
            return new JsonResult(400);
        }
        try {
            if(Objects.isNull(newGood.getMaterialId())){
                //新增
                newGood.setStatus(NewGood.DEFAULT);
                newGoodRepository.save(newGood);
                return new JsonResult(200);
            }else {
                //更新
                NewGood good = newGoodRepository.findById(newGood.getMaterialId()).get();
                if(!NewGood.BING_SUPPLIER.equals(good.getStatus())){
                    BeanUtils.copyProperties(newGood, good);
                    good.setStatus(NewGood.DEFAULT);
                    newGoodRepository.save(good);
                    return new JsonResult(200);
                }
                return new JsonResult(400);
            }
        }catch (Exception e){
            log.error("保存新商品信息失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<NewGood> search(NewGood newGood) {
        return newGoodRepository.findAll(new NewGoodSpecification(newGood));
    }

    @Override
    public NewGood findNewGoodById(Long materialId) {
        return newGoodRepository.findById(materialId).get();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult deleteNewGoodById(Long materialId) {
        if(Objects.isNull(materialId)){
            return new JsonResult(500);
        }
        try {
            newGoodRepository.deleteById(materialId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除失败。发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult add2WaitReview(Long materialId) {
        if(Objects.isNull(materialId)){
            return new JsonResult(400);
        }
        try{
            NewGood newGood = newGoodRepository.findById(materialId).get();
            if(newGood.getStatus().equals(NewGood.DEFAULT)){
                newGood.setStatus(NewGood.NODO);
                newGoodRepository.save(newGood);
                return new JsonResult(200);
            }
            return new JsonResult(500);
        }catch (Exception e){
            log.error("添加到待判断失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult checkNewGood(Long materialId, String flag) {
        if(Objects.isNull(materialId) || StringUtils.isBlank(flag)){
            return new JsonResult(400);
        }
        try {
            NewGood newGood = newGoodRepository.findById(materialId).get();
            if(!NewGood.WAIT.equals(newGood.getStatus())){
                return new JsonResult(400);
            }
            if(NewGood.YES.equals(Integer.valueOf(flag))){
                newGood.setStatus(NewGood.YES);
            }else if(NewGood.ERROR.equals(Integer.valueOf(flag))){
                newGood.setStatus(NewGood.ERROR);
            }
            newGoodRepository.save(newGood);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("检查新品失败，发生异常:{}",e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult add2WaitCheck(Long materialId) {
        if(Objects.isNull(materialId)){
            return new JsonResult(400);
        }
        try {
            NewGood newGood = newGoodRepository.findById(materialId).get();
            if(!NewGood.NODO.equals(newGood.getStatus())){
                return new JsonResult(400);
            }
            newGood.setStatus(NewGood.WAIT);
            newGoodRepository.save(newGood);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("添加到待核查失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult productOrNotProduct(Long materialId, String flag) {
        if(Objects.isNull(materialId) || StringUtils.isBlank(flag)){
            return new JsonResult(400);
        }
        try {
            NewGood newGood = newGoodRepository.findById(materialId).get();
            if(!NewGood.YES.equals(newGood.getStatus())){
                return new JsonResult(400);
            }
            if(NewGood.CAN_PRODUCT.equals(Integer.valueOf(flag))){
                newGood.setStatus(NewGood.CAN_PRODUCT);
            }else if(NewGood.CANT_PRODUCT.equals(Integer.valueOf(flag))){
                newGood.setStatus(NewGood.CANT_PRODUCT);
            }
            newGoodRepository.save(newGood);
            return new JsonResult(200);
        }catch (Exception e){
            log.info("设置失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
