package com.zz.book.domain.listener;

import com.zz.book.domain.event.OrderEvent;
import com.zz.book.service.IZzOrderItem;
import com.zz.common.dto.ZzOrderDTO;
import com.zz.common.dto.ZzOrderItemDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final IZzOrderItem zzOrderItemService;
    private final ModelMapper modelMapper;

    @EventListener
    public void onOrderEvent(OrderEvent event) {
        ZzOrderDTO orderDTO = event.getOrderDTO();
        List<ZzOrderItemDTO> orderItemDTOs = orderDTO.getZzOrderItems();

        switch (event.getEventType()) {
            case ORDER_CREATED:
                createOrderItems(orderDTO, orderItemDTOs);
                break;
            case ORDER_DELETED:
                deleteOrderItems(orderDTO);
                break;
        }
    }

    private void createOrderItems(ZzOrderDTO orderDTO, List<ZzOrderItemDTO> orderItemDTOs) {
        for (ZzOrderItemDTO itemDTO : orderItemDTOs) {
            itemDTO.setZzOrderId(orderDTO.getZzOrderId());
            zzOrderItemService.zzCreate(itemDTO);
        }
    }

    private void deleteOrderItems(ZzOrderDTO orderDTO) {
        for (ZzOrderItemDTO itemDTO : zzOrderItemService.findByOrderId(orderDTO.getZzOrderId())) {
            zzOrderItemService.zzDelete(itemDTO.getZzItemId());
        }
    }

}