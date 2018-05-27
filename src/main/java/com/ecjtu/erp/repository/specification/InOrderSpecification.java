package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.purchase.InOrder;
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
 * @Date: Created in 17:36 2018/5/14
 * @Modified By:
 */
public class InOrderSpecification implements Specification<InOrder> {
    private InOrder inOrder;

    public InOrderSpecification(InOrder inOrder) {
        super();
        this.inOrder = inOrder;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<InOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(inOrder)){
            //入库单编号
            if(!Objects.isNull(inOrder.getInOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("inOrderId"), inOrder.getInOrderId()));
            }
            //所属付款单号
            if(!Objects.isNull(inOrder.getPayOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("payOrderId"), inOrder.getPayOrderId()));
            }
            //状态
            if(!Objects.isNull(inOrder.getStatus())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("status"), inOrder.getStatus()));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
