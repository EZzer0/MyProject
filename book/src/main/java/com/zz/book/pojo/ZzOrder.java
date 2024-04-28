package com.zz.book.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zz.common.pojo.ZzBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单表
 */
@Schema(description = "订单表")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_Order`")
public class ZzOrder extends ZzBaseEntity {
    public static final String COL_ZZ_ORDER_ID = "zz_order_id";
    public static final String COL_ZZ_CUSTOMER_ID = "zz_customer_id";
    public static final String COL_ZZ_ORDER_TOTAL = "zz_order_total";
    /**
     * 订单主键
     */
    @TableId(value = "zz_order_id", type = IdType.AUTO)
    @Schema(description = "订单主键")
    private Long zzOrderId;
    /**
     * 客户主键
     */
    @TableField(value = "`zz_customer_id`")
    @Schema(description = "客户主键")
    private Long zzCustomerId;
    /**
     * 订单总金额
     */
    @TableField(value = "`zz_order_total`")
    @Schema(description = "订单总金额")
    private BigDecimal zzOrderTotal;
}