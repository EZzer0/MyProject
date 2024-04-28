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
 * 权限表
 */
@Schema(description = "权限表")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_Permission`")
public class ZzPermission extends ZzBaseEntity {
    public static final String COL_ZZ_PERMISSION_ID = "zz_permission_id";
    public static final String COL_ZZ_PERMISSION_NAME = "zz_permission_name";
    public static final String COL_ZZ_PERMISSION_CODE = "zz_permission_code";
    public static final String COL_ZZ_PARENT_ID = "zz_parent_id";
    /**
     * 权限主键
     */
    @TableId(value = "zz_permission_id", type = IdType.AUTO)
    @Schema(description = "权限主键")
    private Long zzPermissionId;
    /**
     * 权限名称
     */
    @TableField(value = "`zz_permission_name`")
    @Schema(description = "权限名称")
    private String zzPermissionName;
    /**
     * 权限标识码
     */
    @TableField(value = "`zz_permission_code`")
    @Schema(description = "权限标识码")
    private String zzPermissionCode;
    /**
     * 父权限ID，NULL表示顶级权限
     */
    @TableField(value = "`zz_parent_id`")
    @Schema(description = "父权限ID，NULL表示顶级权限")
    private Long zzParentId;
}