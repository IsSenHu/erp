package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.supplier.Contacts;
import com.ecjtu.erp.model.supplier.Supplier;
import com.ecjtu.erp.model.supplier.vo.ContactsVo;
import com.ecjtu.erp.model.vo.contacts.ReturnVo;
import com.ecjtu.erp.repository.ContactsRepository;
import com.ecjtu.erp.repository.SupplierRepository;
import com.ecjtu.erp.repository.specification.ContactsSpecification;
import com.ecjtu.erp.service.ContactsService;
import com.ecjtu.erp.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 14:42 2018/5/6
 * @Modified By:
 */
@Service
public class ContactsServiceImpl extends BaseServiceImpl implements ContactsService {

    private final static Logger log = LoggerFactory.getLogger(ContactsServiceImpl.class);

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public JsonResult saveContacts(Contacts contacts) {
        try {
            if(Objects.isNull(contacts.getContactsId())){
                //新增
                if(!Objects.isNull(contactsRepository.save(contacts))){
                    return new JsonResult(200);
                }else {
                    return new JsonResult(500);
                }
            }else {
                //更新
                Contacts old = contactsRepository.findById(contacts.getContactsId()).get();
                BeanUtils.copyProperties(contacts, old);
                if(!Objects.isNull(contactsRepository.save(old))){
                    return new JsonResult(200);
                }else {
                    return new JsonResult(500);
                }
            }
        }catch (Exception e){
            log.error("保存联系人失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<ContactsVo> search(Contacts contacts) {
        List<ContactsVo> vos = new ArrayList<>();
        List<Contacts> contactsList = contactsRepository.findAll(new ContactsSpecification(contacts));
        for(Contacts c : contactsList){
            ContactsVo vo = new ContactsVo();
            BeanUtils.copyProperties(c, vo);
            Supplier supplier = supplierRepository.findById(c.getSupplierId()).get();
            vo.setSupplierName(Objects.isNull(supplier) ? "" : supplier.getName());
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public JsonResult deleteContacts(Long contactsId) {
        try {
            contactsRepository.deleteById(contactsId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除联系人失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public ReturnVo<Supplier, Contacts> findContactsById(Long contactsId) {
        ReturnVo<Supplier, Contacts> returnVo = new ReturnVo<>();
        returnVo.setList(supplierRepository.findAll());
        returnVo.setData(contactsRepository.findById(contactsId).get());
        return returnVo;
    }
}
