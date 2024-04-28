package com.zz.system.domain.event;

import com.zz.common.dto.ZzRoleDTO;
import com.zz.common.pojo.EventType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class ZzRoleChangeEvent extends ApplicationEvent {
    private final EventType eventType;
    private final ZzRoleDTO roleDTO;

    public ZzRoleChangeEvent(Object source, EventType eventType, ZzRoleDTO roleDTO) {
        super(source);
        this.eventType = eventType;
        this.roleDTO = roleDTO;
    }
}
