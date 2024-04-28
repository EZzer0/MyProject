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
 * 角色表
 */
@Schema(description = "角色表")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_Role`")
public class ZzRole extends ZzBaseEntity {
    public static final String COL_ZZ_ROLE_ID = "zz_role_id";
    public static final String COL_ZZ_ROLE_NAME = "zz_role_name";
    public static final String COL_ZZ_ROLE_CODE = "zz_role_code";
    public static final String COL_ZZ_PARENT_ID = "zz_parent_id";
    /**
     * 角色主键
     */
    @TableId(value = "zz_role_id", type = IdType.AUTO)
    @Schema(description = "角色主键")
    private Long zzRoleId;
    /**
     * 角色名称
     */
    @TableField(value = "`zz_role_name`")
    @Schema(description = "角色名称")
    private String zzRoleName;
    /**
     * 角色标识
     */
    @TableField(value = "`zz_role_code`")
    @Schema(description = "角色标识")
    private String zzRoleCode;
    /**
     * 父角色ID，NULL表示顶级角色
     */
    @TableField(value = "`zz_parent_id`")
    @Schema(description = "父角色ID，NULL表示顶级角色")
    private Long zzParentId;
}