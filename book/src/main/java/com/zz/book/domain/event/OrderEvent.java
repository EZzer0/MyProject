package com.zz.book.domain.event;

import com.zz.common.dto.ZzOrderDTO;
import com.zz.common.pojo.EventType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderEvent extends ApplicationEvent {
    private final EventType eventType;
    private final ZzOrderDTO orderDTO;

    public OrderEvent(Object source, EventType eventType, ZzOrderDTO orderDTO) {
        super(source);
        this.eventType = eventType;
        this.orderDTO = orderDTO;
    }
}
