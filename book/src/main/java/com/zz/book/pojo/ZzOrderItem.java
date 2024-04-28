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
 * 订单项目表
 */
@Schema(description = "订单项目表")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_OrderItem`")
public class ZzOrderItem extends ZzBaseEntity {
    public static final String COL_ZZ_ITEM_ID = "zz_item_id";
    public static final String COL_ZZ_ORDER_ID = "zz_order_id";
    public static final String COL_ZZ_BOOK_ID = "zz_book_id";
    public static final String COL_ZZ_QUANTITY = "zz_quantity";
    public static final String COL_ZZ_PRICE = "zz_price";
    /**
     * 订单项目主键
     */
    @TableId(value = "zz_item_id", type = IdType.AUTO)
    @Schema(description = "订单项目主键")
    private Long zzItemId;
    /**
     * 订单主键
     */
    @TableField(value = "`zz_order_id`")
    @Schema(description = "订单主键")
    private Long zzOrderId;
    /**
     * 书籍主键
     */
    @TableField(value = "`zz_book_id`")
    @Schema(description = "书籍主键")
    private Long zzBookId;
    /**
     * 数量
     */
    @TableField(value = "`zz_quantity`")
    @Schema(description = "数量")
    private Integer zzQuantity;
    /**
     * 价格
     */
    @TableField(value = "`zz_price`")
    @Schema(description = "价格")
    private BigDecimal zzPrice;
}