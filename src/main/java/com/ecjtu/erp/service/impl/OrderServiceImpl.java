package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.order.Item;
import com.ecjtu.erp.model.order.Order;
import com.ecjtu.erp.model.order.OutOrder;
import com.ecjtu.erp.model.order.ReceiveOrder;
import com.ecjtu.erp.model.order.vo.ItemVo;
import com.ecjtu.erp.model.supplier.SupplierGood;
import com.ecjtu.erp.repository.*;
import com.ecjtu.erp.repository.specification.OrderSpecification;
import com.ecjtu.erp.service.OrderService;
import com.ecjtu.erp.util.JsonResult;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:43 2018/5/14
 * @Modified By:
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SupplierGoodRepository supplierGoodRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OutOrderRepository outOrderRepository;

    @Autowired
    private ReceiveOrderRepository receiveOrderRepository;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveOrder(Order order) {
        if(Objects.isNull(order)){
            return new JsonResult(500);
        }
        try {
            if(Objects.isNull(order.getOrderId())){
                log.info("新增订单:{}", order);
                order.setStatus(Order.DEFAULT);
                order.setCreateTime(new Date());
                order.setMoney(0.00);
                orderRepository.save(order);
            }else {
                log.info("修改订单:{}", order);
                Order old = orderRepository.findById(order.getOrderId()).get();
                old.setPayType(order.getPayType());
                old.setCurrencyUnitId(order.getCurrencyUnitId());
                old.setDescription(order.getDescription());
                orderRepository.save(old);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存订单失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(200);
        }
    }

    @Override
    public List<Order> search(Order order) {
        return orderRepository.findAll(new OrderSpecification(order));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveItem(Item item) {
        if(Objects.isNull(item)){
            return new JsonResult(500);
        }
        try {
            List<Item> items = itemRepository.findAllBySupplierGoodIdAndOrderId(item.getSupplierGoodId(), item.getOrderId());
            SupplierGood supplierGood = supplierGoodRepository.findById(item.getSupplierGoodId()).get();
            if(supplierGood.getCurrentInventory() < item.getNumber()){
                return new JsonResult(401, "库存不足，请确认数量");
            }
            Order order = orderRepository.findById(item.getOrderId()).get();
            supplierGood.setCurrentInventory(supplierGood.getCurrentInventory() - item.getNumber());
            if(CollectionUtils.isNotEmpty(items)){
                log.info("{}订单里已经有该商品了:{}", item.getOrderId(), item.getSupplierGoodId());
                Item exist = items.get(0);
                exist.setNumber(exist.getNumber() + item.getNumber());
                exist.setMoney(new BigDecimal(supplierGood.getSale()).multiply(new BigDecimal(exist.getNumber())).setScale(2).doubleValue());
                itemRepository.save(exist);
            }else {
                item.setMoney(new BigDecimal(supplierGood.getSale()).multiply(new BigDecimal(item.getNumber())).setScale(2).doubleValue());
                itemRepository.save(item);
            }
            List<Item> itemList = itemRepository.findAllByOrderId(item.getOrderId());
            BigDecimal total = new BigDecimal(0);
            for(Item item1 : itemList){
                total = total.add(new BigDecimal(item1.getMoney())).setScale(2);
            }
            order.setMoney(total.doubleValue());
            orderRepository.save(order);
            supplierGoodRepository.save(supplierGood);
            return new JsonResult(200);
        }catch (Exception e){
            log.info("新增订单条目失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<ItemVo> findAllItemByOrderId(Long orderId) {
        return itemRepository.findAllByOrderId(orderId)
                .stream().map(item -> {
                    ItemVo itemVo = new ItemVo();
                    BeanUtils.copyProperties(item, itemVo);
                    itemVo.setGood(supplierGoodRepository.findById(item.getSupplierGoodId()).get());
                    return itemVo;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult setOrderStatus(Long orderId, Integer flag) {
        if(Objects.isNull(orderId) || Objects.isNull(flag)){
            return new JsonResult(400);
        }
        try {
            List<Item> items = itemRepository.findAllByOrderId(orderId);
            if(CollectionUtils.isEmpty(items)){
                return new JsonResult(401, "订单没有条目，不能通过");
            }
            Order order = orderRepository.findById(orderId).get();
            if(Order.CAN.equals(flag)){
                order.setStatus(Order.CAN);
                orderRepository.save(order);
                //生成出库单和收款单
                createOutOrderAndReceiveOrder(orderId);
            }else if(Order.CANT.equals(flag)){
                order.setStatus(Order.CANT);
                orderRepository.save(order);
                //还原库存
                originStock(orderId);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("审核订单失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    /**
     * 还原库存
     * @param orderId
     */
    private void originStock(Long orderId) {
        List<Item> items = itemRepository.findAllByOrderId(orderId);
        items.forEach(item -> {
            SupplierGood good = supplierGoodRepository.findById(item.getSupplierGoodId()).get();
            good.setCurrentInventory(good.getCurrentInventory() + item.getNumber());
            supplierGoodRepository.save(good);
        });
    }

    /**
     * 生成出库单和收款单
     * @param orderId
     */
    private void createOutOrderAndReceiveOrder(Long orderId){
        ReceiveOrder receiveOrder = new ReceiveOrder();
        Order order = orderRepository.findById(orderId).get();
        List<Item> items = itemRepository.findAllByOrderId(orderId);
        receiveOrder.setStatus(ReceiveOrder.ING);
        receiveOrder.setCurrencyUnitId(order.getCurrencyUnitId());
        receiveOrder.setNeed(order.getMoney());
        receiveOrder.setOrderId(orderId);
        receiveOrder.setReceived(0.00);
        receiveOrder.setReceiveType(order.getPayType());

        OutOrder outOrder = new OutOrder();
        outOrder.setMoney(order.getMoney());
        outOrder.setStatus(OutOrder.PREPAER);
        outOrder.setOrderId(orderId);

        ReceiveOrder newLt = receiveOrderRepository.save(receiveOrder);
        OutOrder newIt = outOrderRepository.save(outOrder);
        order.setOutOrderId(newIt.getOutOrderId());
        order.setReceiveOrderId(newLt.getReceiveOrderId());

        orderRepository.save(order);

        items.forEach(item -> {
            item.setOutOrderId(newIt.getOutOrderId());
            itemRepository.save(item);
        });
    }
}
