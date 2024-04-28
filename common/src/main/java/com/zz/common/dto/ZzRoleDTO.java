package com.zz.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zz.common.pojo.ZzBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Schema(description = "ZzRole 数据传输对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZzRoleDTO extends ZzBaseDTO {

    @Schema(description = "角色主键")
    @JsonProperty("zzRoleId")
    private Long zzRoleId;

    @Schema(description = "角色名称")
    @JsonProperty("zzRoleName")
    private String zzRoleName;

    @Schema(description = "角色标识")
    @JsonProperty("zzRoleCode")
    private String zzRoleCode;

    @Schema(description = "父角色ID，NULL表示顶级角色")
    @JsonProperty("zzParentId")
    private Long zzParentId;

    @Schema(description = "是否为拥有该角色")
    @JsonProperty("zzIsOwned")
    private boolean zzIsOwned;

    @Schema(description = "角色拥有的权限集合")
    @JsonProperty("zzPermissions")
    private List<ZzPermissionDTO> zzPermissions;

    @Schema(description = "角色关联的资源集合")
    @JsonProperty("zzResources")
    private List<ZzResourceDTO> zzResources;

}

