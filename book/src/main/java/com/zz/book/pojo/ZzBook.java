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
 * 书籍表
 */
@Schema(description = "书籍表")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_Book`")
public class ZzBook extends ZzBaseEntity {
    public static final String COL_ZZ_BOOK_ID = "zz_book_id";
    public static final String COL_ZZ_TITLE = "zz_title";
    public static final String COL_ZZ_AUTHOR = "zz_author";
    public static final String COL_ZZ_URL = "zz_url";
    public static final String COL_ZZ_PRICE = "zz_price";
    public static final String COL_ZZ_INVENTORY_COUNT = "zz_inventory_count";
    /**
     * 书籍主键
     */
    @TableId(value = "zz_book_id", type = IdType.AUTO)
    @Schema(description = "书籍主键")
    private Long zzBookId;
    /**
     * 书籍标题
     */
    @TableField(value = "`zz_title`")
    @Schema(description = "书籍标题")
    private String zzTitle;
    /**
     * 作者
     */
    @TableField(value = "`zz_author`")
    @Schema(description = "作者")
    private String zzAuthor;
    /**
     * 图像URL
     */
    @TableField(value = "`zz_url`")
    @Schema(description = "图像URL")
    private String zzUrl;
    /**
     * 价格
     */
    @TableField(value = "`zz_price`")
    @Schema(description = "价格")
    private BigDecimal zzPrice;
    /**
     * 库存数量
     */
    @TableField(value = "`zz_inventory_count`")
    @Schema(description = "库存数量")
    private Integer zzInventoryCount;
}