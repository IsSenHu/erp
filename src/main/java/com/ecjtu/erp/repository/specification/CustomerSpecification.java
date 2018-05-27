package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.custom.Customer;
import org.apache.commons.lang3.StringUtils;
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
 * @Author: HuSen
 * @Description:
 * @Date: Created in 23:45 2018/5/14
 * @Modified By:
 */
public class CustomerSpecification implements Specification<Customer> {
    private Customer customer;

    public CustomerSpecification(Customer customer) {
        super();
        this.customer = customer;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(customer)){
            //客户编号
            if(!Objects.isNull(customer.getCustomerId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("customerId"), customer.getCustomerId()));
            }
            //客户名称
            if(StringUtils.isNotBlank(customer.getName())){
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + customer.getName() + "%"));
            }
            //电话
            if(StringUtils.isNotBlank(customer.getPhone())){
                predicates.add(criteriaBuilder.like(root.get("phone").as(String.class), "%" + customer.getPhone() + "%"));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
