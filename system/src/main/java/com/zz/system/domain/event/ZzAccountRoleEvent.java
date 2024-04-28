package com.zz.system.domain.event;

import com.zz.common.dto.ZzAccountDTO;
import com.zz.common.pojo.EventType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ZzAccountRoleEvent extends ApplicationEvent {
    private final EventType eventType;
    private final ZzAccountDTO accountDTO;

    public ZzAccountRoleEvent(Object source, EventType eventType, ZzAccountDTO accountDTO) {
        super(source);
        this.eventType = eventType;
        this.accountDTO = accountDTO;
    }
}
