package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.good.CustomsCode;
import com.ecjtu.erp.repository.CustomsCodeRepository;
import com.ecjtu.erp.service.CustomsCodeService;
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
 * @Date: Created in 11:19 2018/5/13
 * @Modified By:
 */
@Service
public class CustomsCodeServiceImpl extends BaseServiceImpl implements CustomsCodeService {
    private static final Logger log = LoggerFactory.getLogger(CustomsCodeServiceImpl.class);
    @Autowired
    private CustomsCodeRepository customsCodeRepository;

    @Override
    public List findAll() {
        return customsCodeRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveCustomsCode(CustomsCode customsCode) {
        if(Objects.isNull(customsCode)){
            return new JsonResult(500);
        }
        try {
            if(Objects.isNull(customsCode.getId())){
                log.info("新增海关编码:{}", customsCode);
                customsCodeRepository.save(customsCode);
            }else {
                log.info("修改海关编码:{}", customsCode);
                CustomsCode old = customsCodeRepository.findById(customsCode.getId()).get();
                BeanUtils.copyProperties(customsCode, old);
                customsCodeRepository.save(old);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存海关编码失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public CustomsCode findCustomsCodeById(Long id) {
        return customsCodeRepository.findById(id).get();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult deleteCustomsCodeById(Long id) {
        if(Objects.isNull(id)){
            return new JsonResult(400);
        }
        try {
            customsCodeRepository.deleteById(id);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除海关编码失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
