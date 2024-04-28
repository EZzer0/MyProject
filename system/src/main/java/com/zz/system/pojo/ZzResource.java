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

import java.util.List;

/**
 * 资源表
 */
@Schema(description = "资源表")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`Zz`.`Zz_Resource`")
public class ZzResource extends ZzBaseEntity {
    public static final String COL_ZZ_RESOURCE_ID = "zz_resource_id";
    public static final String COL_ZZ_RESOURCE_NAME = "zz_resource_name";
    public static final String COL_ZZ_PARENT_ID = "zz_parent_id";
    public static final String COL_ZZ_LINK = "zz_link";
    public static final String COL_ZZ_ORDER = "zz_order";
    public static final String COL_ZZ_RESOURCE_TYPE = "zz_resource_type";
    /**
     * 资源主键
     */
    @TableId(value = "zz_resource_id", type = IdType.AUTO)
    @Schema(description = "资源主键")
    private Long zzResourceId;
    /**
     * 资源名称
     */
    @TableField(value = "`zz_resource_name`")
    @Schema(description = "资源名称")
    private String zzResourceName;
    /**
     * 父资源ID，NULL表示顶级资源
     */
    @TableField(value = "`zz_parent_id`")
    @Schema(description = "父资源ID，NULL表示顶级资源")
    private Long zzParentId;
    /**
     * 链接
     */
    @TableField(value = "`zz_link`")
    @Schema(description = "链接")
    private String zzLink;
    /**
     * 显示顺序
     */
    @TableField(value = "`zz_order`")
    @Schema(description = "显示顺序")
    private Integer zzOrder;
    /**
     * 资源类型(0:目录, 1:菜单, 2:按钮)
     */
    @TableField(value = "`zz_resource_type`")
    @Schema(description = "资源类型(0:目录, 1:菜单, 2:按钮)")
    private Byte zzResourceType;
    @TableField(exist = false)
    private List<ZzResource> zzChildren;
}