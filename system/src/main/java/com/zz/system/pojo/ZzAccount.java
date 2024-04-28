package com.zz.system.pojo;

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

/**
 * 账号表
 */
@Schema(description = "账号表")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_Account`")
public class ZzAccount extends ZzBaseEntity {
    public static final String COL_ZZ_ACCOUNT_ID = "zz_account_id";
    public static final String COL_ZZ_USERNAME = "zz_username";
    public static final String COL_ZZ_PASSWORD = "zz_password";
    public static final String COL_ZZ_SALT = "zz_salt";
    public static final String COL_ZZ_REAL_NAME = "zz_real_name";
    public static final String COL_ZZ_SEX = "zz_sex";
    public static final String COL_ZZ_EMAIL = "zz_email";
    public static final String COL_ZZ_PHONE = "zz_phone";
    /**
     * 顾客主键
     */
    @TableId(value = "zz_account_id", type = IdType.AUTO)
    @Schema(description = "顾客主键")
    private Long zzAccountId;
    /**
     * 顾客名
     */
    @TableField(value = "`zz_username`")
    @Schema(description = "顾客名")
    private String zzUsername;
    /**
     * 密码
     */
    @TableField(value = "`zz_password`")
    @Schema(description = "密码")
    private String zzPassword;
    /**
     * 加密盐
     */
    @TableField(value = "`zz_salt`")
    @Schema(description = "加密盐")
    private String zzSalt;
    /**
     * 真实姓名
     */
    @TableField(value = "`zz_real_name`")
    @Schema(description = "真实姓名")
    private String zzRealName;
    /**
     * 性别（M: 男, F: 女, N: 不明）
     */
    @TableField(value = "`zz_sex`")
    @Schema(description = "性别（M: 男, F: 女, N: 不明）")
    private String zzSex;
    /**
     * 邮箱
     */
    @TableField(value = "`zz_email`")
    @Schema(description = "邮箱")
    private String zzEmail;
    /**
     * 手机号
     */
    @TableField(value = "`zz_phone`")
    @Schema(description = "手机号")
    private String zzPhone;
}