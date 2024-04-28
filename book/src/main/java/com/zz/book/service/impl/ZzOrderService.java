package com.zz.book.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zz.book.domain.event.OrderEvent;
import com.zz.book.mapper.ZzOrderMapper;
import com.zz.book.pojo.ZzBook;
import com.zz.book.pojo.ZzCustomer;
import com.zz.book.pojo.ZzOrder;
import com.zz.book.pojo.ZzOrderItem;
import com.zz.book.service.IZzOrder;
import com.zz.common.dto.ZzOrderDTO;
import com.zz.common.dto.ZzOrderItemDTO;
import com.zz.common.pojo.EventType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZzOrderService extends MPJBaseServiceImpl<ZzOrderMapper, ZzOrder> implements IZzOrder {

    private final ApplicationEventPublisher eventPublisher;
    private final ModelMapper modelMapper;

    @Autowired
    public ZzOrderService(ApplicationEventPublisher eventPublisher, ModelMapper modelMapper) {
        this.eventPublisher = eventPublisher;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public String zzCreate(ZzOrderDTO orderDTO) {
        ZzOrder order = modelMapper.map(orderDTO, ZzOrder.class);
        if (save(order)) {
            orderDTO.setZzOrderId(order.getZzOrderId());
            System.out.println(orderDTO);
            eventPublisher.publishEvent(new OrderEvent(this, EventType.ORDER_CREATED, orderDTO));
            return "订单创建成功";
        } else {
            throw new IllegalStateException("创建订单失败。");
        }
    }

    @Transactional
    @Override
    public String zzDelete(ZzOrderDTO orderDTO) {
        ZzOrder order = modelMapper.map(orderDTO, ZzOrder.class);
        if (!removeById(order.getZzOrderId())) {
            throw new IllegalStateException("订单未找到或无法删除。");
        }
        eventPublisher.publishEvent(new OrderEvent(this, EventType.ORDER_DELETED, orderDTO));
        return "订单删除成功";
    }

    @Transactional
    @Override
    public String zzUpdate(ZzOrderDTO zzOrderDTO) {
        ZzOrder existingOrder = getById(zzOrderDTO.getZzOrderId());
        if (existingOrder == null) {
            throw new IllegalStateException("指定的订单未找到。");
        }
        modelMapper.map(zzOrderDTO, existingOrder);
        if (updateById(existingOrder)) {
            return "订单更新成功";
        } else {
            throw new IllegalStateException("更新订单失败。");
        }
    }


    @Override
    public IPage<ZzOrderDTO> zzFindAll(int current, int size) {
        return selectJoinListPage(new Page<>(current, size), ZzOrderDTO.class,
                new MPJLambdaWrapper<ZzOrder>()
                        .selectAll(ZzOrder.class)
                        .leftJoin(ZzOrderItem.class, ZzOrderItem::getZzOrderId, ZzOrder::getZzOrderId)
                        .leftJoin(ZzBook.class, ZzBook::getZzBookId, ZzOrderItem::getZzBookId)
                        .leftJoin(ZzCustomer.class, ZzCustomer::getZzCustomerId, ZzOrder::getZzCustomerId)
                        .selectCollection(ZzOrderItem.class, ZzOrderDTO::getZzOrderItems, b -> b
                                .association(ZzBook.class, ZzOrderItemDTO::getZzBook) // 确保映射书籍
                                .association(ZzCustomer.class, ZzOrderItemDTO::getZzCustomer)) // 确保映射客户
        );
    }

    @Override
    public ZzOrderDTO zzDetails(Long id) {
        return selectJoinOne(ZzOrderDTO.class,
                new MPJLambdaWrapper<ZzOrder>()
                        .selectAll(ZzOrder.class)
                        .selectCollection(ZzOrderItem.class, ZzOrderDTO::getZzOrderItems)
                        .leftJoin(ZzOrderItem.class, ZzOrderItem::getZzOrderId, ZzOrder::getZzOrderId)
                        .eq(ZzOrder::getZzOrderId, id)
        );
    }


}