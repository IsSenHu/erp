package com.ecjtu.erp.tran;

import com.ecjtu.erp.model.supplier.Contacts;
import com.ecjtu.erp.model.supplier.Supplier;
import com.ecjtu.erp.model.supplier.vo.SupplierVo;
import com.ecjtu.erp.repository.ContactsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 23:24 2018/5/13
 * @Modified By:
 */
@Component
public class SupplierPoToVo implements Function<Supplier, SupplierVo> {

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public SupplierVo apply(Supplier supplier) {
        SupplierVo supplierVo = new SupplierVo();
        if(!Objects.isNull(supplier)){
            BeanUtils.copyProperties(supplier, supplierVo);
            List<Contacts> contacts = contactsRepository.findAllBySupplierId(supplier.getSupplierId());
            if(!CollectionUtils.isEmpty(contacts)){
                supplierVo.setContacts(contacts.get(0));
            }
        }
        return supplierVo;
    }
}
