package com.zz.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色资源关联表
 */
@Schema(description = "角色资源关联表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_Role_Resource`")
public class ZzRoleResource {
    public static final String COL_ZZ_ROLE_ID = "zz_role_id";
    public static final String COL_ZZ_RESOURCE_ID = "zz_resource_id";
    /**
     * 角色主键
     */
    @TableField(value = "zz_role_id")
    @Schema(description = "角色主键")
    private Long zzRoleId;
    /**
     * 资源主键
     */
    @TableField(value = "zz_resource_id")
    @Schema(description = "资源主键")
    private Long zzResourceId;
}