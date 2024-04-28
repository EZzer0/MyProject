package com.zz.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账户角色关联表
 */
@Schema(description = "账户角色关联表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_Account_Role`")
public class ZzAccountRole {
    public static final String COL_ZZ_ACCOUNT_ID = "zz_account_id";
    public static final String COL_ZZ_ROLE_ID = "zz_role_id";
    /**
     * 账户主键
     */
    @TableField(value = "zz_account_id")
    @Schema(description = "账户主键")
    private Long zzAccountId;
    /**
     * 角色主键
     */
    @TableField(value = "zz_role_id")
    @Schema(description = "角色主键")
    private Long zzRoleId;
}