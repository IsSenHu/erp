package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.order.OutOrder;
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
 * @Date: Created in 14:44 2018/5/15
 * @Modified By:
 */
public class OutOrderSpecification implements Specification<OutOrder> {
    private OutOrder outOrder;

    public OutOrderSpecification(OutOrder outOrder) {
        super();
        this.outOrder = outOrder;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<OutOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(outOrder)){
            //出库单号
            if(!Objects.isNull(outOrder.getOutOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("outOrderId"), outOrder.getOutOrderId()));
            }
            //订单号
            if(!Objects.isNull(outOrder.getOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("orderId"), outOrder.getOrderId()));
            }
            //状态
            if(!Objects.isNull(outOrder.getStatus())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("status"),outOrder.getStatus()));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
