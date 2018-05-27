package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.order.Order;
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
 * @Date: Created in 8:04 2018/5/15
 * @Modified By:
 */
public class OrderSpecification implements Specification<Order> {
    private Order order;

    public OrderSpecification(Order order) {
        super();
        this.order = order;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(order)){
            //订单号
            if(!Objects.isNull(order.getOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("orderId"), order.getOrderId()));
            }
            //出库单号
            if(!Objects.isNull(order.getOutOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("outOrderId"), order.getOutOrderId()));
            }
            //收款单号
            if(!Objects.isNull(order.getReceiveOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("receiveOrderId"), order.getReceiveOrderId()));
            }
            //状态
            if(!Objects.isNull(order.getStatus())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("status"), order.getStatus()));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
