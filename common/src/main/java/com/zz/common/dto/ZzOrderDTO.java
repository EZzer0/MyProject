package com.zz.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zz.common.pojo.ZzBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "ZzOrder 数据传输对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZzOrderDTO extends ZzBaseDTO {

    @Schema(description = "订单主键")
    @JsonProperty("zzOrderId")
    private Long zzOrderId;

    @Schema(description = "客户主键")
    @JsonProperty("zzCustomerId")
    private Long zzCustomerId;

    @Schema(description = "订单总金额")
    @JsonProperty("zzOrderTotal")
    private BigDecimal zzOrderTotal;

    @Schema(description = "订单项列表")
    @JsonProperty("zzOrderItems")
    private List<ZzOrderItemDTO> zzOrderItems;
}

