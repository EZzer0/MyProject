package com.zz.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zz.common.pojo.ZzBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Schema(description = "ZzOrderItem 数据传输对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZzOrderItemDTO extends ZzBaseDTO {

    @Schema(description = "订单项目主键")
    @JsonProperty("zzItemId")
    private Long zzItemId;

    @Schema(description = "订单主键")
    @JsonProperty("zzOrderId")
    private Long zzOrderId;

    @Schema(description = "书籍主键")
    @JsonProperty("zzBookId")
    private Long zzBookId;

    @Schema(description = "数量")
    @JsonProperty("zzQuantity")
    private Integer zzQuantity;

    @Schema(description = "价格")
    @JsonProperty("zzPrice")
    private BigDecimal zzPrice;

    @Schema(description = "书籍集合")
    @JsonProperty("zzBooks")
    private ZzBookDTO zzBook;

    @Schema(description = "客户集合")
    @JsonProperty("zzCustomers")
    private ZzCustomerDTO zzCustomer;


}

