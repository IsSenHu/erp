package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.supplier.Contacts;
import com.ecjtu.erp.model.supplier.Supplier;
import com.ecjtu.erp.model.supplier.vo.SupplierVo;
import com.ecjtu.erp.repository.ContactsRepository;
import com.ecjtu.erp.repository.SupplierRepository;
import com.ecjtu.erp.repository.specification.ContactsSpecification;
import com.ecjtu.erp.repository.specification.SupplierSpecification;
import com.ecjtu.erp.service.SupplierService;
import com.ecjtu.erp.util.JsonResult;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 14:29 2018/5/6
 * @Modified By:
 */
@Service
public class SupplierServiceImpl extends BaseServiceImpl implements SupplierService {
    private static final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public List<Supplier> findAllSupplier() {
        return supplierRepository.findAll();
    }

    @Override
    @Transactional
    public JsonResult saveSupplier(Supplier supplier) {
        if(Objects.isNull(supplier)){
            return new JsonResult(400);
        }
        try {
            if(Objects.isNull(supplier.getSupplierId())){
                //新增
                supplier.setStatus(Supplier.NODO);
                supplier.setLevel(Supplier.DEFAULT_LEVEL);
                supplierRepository.save(supplier);
            }else {
                //更新 供应商状态为默认状态，可重新审核
                Supplier old = supplierRepository.findById(supplier.getSupplierId()).get();
                old.setName(supplier.getName());
                old.setDescription(supplier.getDescription());
                old.setStatus(Supplier.NODO);
                old.setLevel(Objects.isNull(supplier.getLevel()) ? old.getLevel() : supplier.getLevel());
                supplierRepository.save(old);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存供应商失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<SupplierVo> search(Supplier supplier) {
        log.info("asdsddadas:{}", supplier.getSupplierId());
        List<SupplierVo> vos = new ArrayList<>();
        List<Supplier> suppliers = supplierRepository.findAll(new SupplierSpecification(supplier));
        for(Supplier s : suppliers){
            SupplierVo vo = new SupplierVo();
            BeanUtils.copyProperties(s, vo);
            List<Contacts> contactsList = contactsRepository.findAllBySupplierId(s.getSupplierId());
            log.info("asdsadasdsa:{}", contactsList.size());
            if(CollectionUtils.isNotEmpty(contactsList)){
                vo.setContactsName(contactsList.get(0).getName());
                vo.setContactsId(contactsList.get(0).getContactsId());
            }
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public Supplier findSupplierById(Long supplierId) {
        if(Objects.isNull(supplierId)){
            return null;
        }
        return supplierRepository.findById(supplierId).get();
    }

    @Override
    @Transactional
    public JsonResult deleteSupplier(Long supplierId) {
        if(Objects.isNull(supplierId)){
            return new JsonResult(400);
        }
        try {
            supplierRepository.deleteById(supplierId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除供应商失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional
    public JsonResult accessSupplierOrNot(Long supplierId, String flag) {
        if(Objects.isNull(supplierId)){
            return new JsonResult(400);
        }
        try {
            Supplier supplier = supplierRepository.findById(supplierId).get();
            if(Objects.isNull(supplier)){
                return new JsonResult(400);
            }
            if(Objects.equals(supplier.getStatus(), Supplier.NODO) && "access".equals(flag)){
                supplier.setStatus(Supplier.ENABLED);
            }else if(Objects.equals(supplier.getStatus(), Supplier.NODO) && "noAccess".equals(flag)){
                supplier.setStatus(Supplier.DISABLED);
            }else if((Objects.equals(supplier.getStatus(), Supplier.ENABLED) || Objects.equals(supplier.getStatus(), Supplier.DISABLED)) && "stop".equals(flag)){
                supplier.setStatus(Supplier.STOP);
            }else if(Objects.equals(supplier.getStatus(), Supplier.STOP) && "start".equals(flag)){
                supplier.setStatus(Supplier.ENABLED);
            }else if(Objects.equals(supplier.getStatus(), Supplier.ENABLED)){
                supplier.setStatus(Supplier.DISABLED);
            }else if(Objects.equals(supplier.getStatus(), Supplier.DISABLED)){
                supplier.setStatus(Supplier.ENABLED);
            }else {
                return new JsonResult(400);
            }
            if(!Objects.isNull(supplierRepository.save(supplier))){
                return new JsonResult(200);
            }else {
                return new JsonResult(500);
            }
        }catch (Exception e){
            log.error("修改状态失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult setAccount(Supplier supplier) {
        if(Objects.isNull(supplier)){
            return new JsonResult(500);
        }
        try {
            if(supplier.getAccountStartDate().toEpochDay() >= supplier.getAccountEndDate().toEpochDay()){
                return new JsonResult(400, "账期结束日期不能小于等于账期开始日期");
            }
            Supplier old = supplierRepository.findById(supplier.getSupplierId()).get();
            if (!Objects.isNull(old.getAccountStartDate()) && old.getAccountStartDate().toEpochDay() != supplier.getAccountStartDate().toEpochDay()){
                return new JsonResult(400, "账期开始日期不能修改");
            }
            if (!Objects.isNull(old.getAccountEndDate()) && old.getAccountEndDate().toEpochDay() > supplier.getAccountEndDate().toEpochDay()){
                return new JsonResult(400, "账期结束日期不能小于之前设置的日期");
            }
            old.setAccountStartDate(supplier.getAccountStartDate());
            old.setAccountEndDate(supplier.getAccountEndDate());
            old.setStatus(Supplier.ACCOUND_PERIOD);
            supplierRepository.save(old);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("账期设置失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
