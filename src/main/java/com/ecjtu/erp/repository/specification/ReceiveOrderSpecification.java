package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.order.ReceiveOrder;
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
 * @Date: Created in 14:33 2018/5/15
 * @Modified By:
 */
public class ReceiveOrderSpecification implements Specification<ReceiveOrder> {
    private ReceiveOrder receiveOrder;

    public ReceiveOrderSpecification(ReceiveOrder receiveOrder) {
        super();
        this.receiveOrder = receiveOrder;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<ReceiveOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(receiveOrder)){
            //收款单号
            if(!Objects.isNull(receiveOrder.getReceiveOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("receiveOrderId"), receiveOrder.getReceiveOrderId()));
            }
            //所属订单
            if(!Objects.isNull(receiveOrder.getOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("orderId"), receiveOrder.getOrderId()));
            }
            //状态
            if(!Objects.isNull(receiveOrder.getStatus())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("status"), receiveOrder.getStatus()));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
