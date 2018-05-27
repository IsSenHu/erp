package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.supplier.Contacts;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 17:44 2018/5/6
 * @Modified By:
 */
public class ContactsSpecification implements Specification<Contacts> {

    private Contacts contacts;

    public ContactsSpecification(Contacts contacts) {
        super();
        this.contacts = contacts;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<Contacts> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(contacts)){
            //联系人编号
            if(!Objects.isNull(contacts.getContactsId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("contactsId"), contacts.getContactsId()));
            }
            //联系人名称
            if(!Objects.isNull(contacts.getName())){
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + contacts.getName() + "%"));
            }
            //供应商编号
            if(!Objects.isNull(contacts.getSupplierId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("supplierId"), contacts.getSupplierId()));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
