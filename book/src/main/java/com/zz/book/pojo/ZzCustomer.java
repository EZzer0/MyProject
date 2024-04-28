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
 * 客户表
 */
@Schema(description = "客户表")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_Customer`")
public class ZzCustomer extends ZzBaseEntity {
    public static final String COL_ZZ_CUSTOMER_ID = "zz_customer_id";
    public static final String COL_ZZ_REAL_NAME = "zz_real_name";
    public static final String COL_ZZ_SEX = "zz_sex";
    public static final String COL_ZZ_AGE = "zz_age";
    public static final String COL_ZZ_EMAIL = "zz_email";
    public static final String COL_ZZ_PHONE = "zz_phone";
    public static final String COL_ZZ_ADDRESS = "zz_address";
    public static final String COL_ZZ_PASSWORD = "zz_password";
    public static final String COL_ZZ_BALANCE = "zz_balance";
    /**
     * 客户主键
     */
    @TableId(value = "zz_customer_id", type = IdType.AUTO)
    @Schema(description = "客户主键")
    private Long zzCustomerId;
    /**
     * 真实姓名
     */
    @TableField(value = "`zz_real_name`")
    @Schema(description = "真实姓名")
    private String zzRealName;
    /**
     * 性别
     */
    @TableField(value = "`zz_sex`")
    @Schema(description = "性别")
    private String zzSex;
    /**
     * 年龄
     */
    @TableField(value = "`zz_age`")
    @Schema(description = "年龄")
    private Byte zzAge;
    /**
     * 邮箱
     */
    @TableField(value = "`zz_email`")
    @Schema(description = "邮箱")
    private String zzEmail;
    /**
     * 电话号码
     */
    @TableField(value = "`zz_phone`")
    @Schema(description = "电话号码")
    private String zzPhone;
    /**
     * 地址
     */
    @TableField(value = "`zz_address`")
    @Schema(description = "地址")
    private String zzAddress;
    /**
     * 密码
     */
    @TableField(value = "`zz_password`")
    @Schema(description = "密码")
    private String zzPassword;
    /**
     * 账户余额
     */
    @TableField(value = "`zz_balance`")
    @Schema(description = "账户余额")
    private BigDecimal zzBalance;
}