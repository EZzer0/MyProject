package com.zz.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色权限关联表
 */
@Schema(description = "角色权限关联表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_Role_Permission`")
public class ZzRolePermission {
    public static final String COL_ZZ_ROLE_ID = "zz_role_id";
    public static final String COL_ZZ_PERMISSION_ID = "zz_permission_id";
    /**
     * 角色主键
     */
    @TableField(value = "zz_role_id")
    @Schema(description = "角色主键")
    private Long zzRoleId;
    /**
     * 权限主键
     */
    @TableField(value = "zz_permission_id")
    @Schema(description = "权限主键")
    private Long zzPermissionId;
}