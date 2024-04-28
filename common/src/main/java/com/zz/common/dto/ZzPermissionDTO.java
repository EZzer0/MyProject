package com.zz.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zz.common.pojo.ZzBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Schema(description = "ZzPermission 数据传输对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZzPermissionDTO extends ZzBaseDTO {

    @Schema(description = "权限主键")
    @JsonProperty("zzPermissionId")
    private Long zzPermissionId;

    @Schema(description = "权限名称")
    @JsonProperty("zzPermissionName")
    private String zzPermissionName;

    @Schema(description = "权限标识码")
    @JsonProperty("zzPermissionCode")
    private String zzPermissionCode;

    @Schema(description = "父权限ID，NULL表示顶级权限")
    @JsonProperty("zzParentId")
    private Long zzParentId;

    @Schema(description = "是否为拥有该权限")
    @JsonProperty("zzIsOwned")
    private boolean zzIsOwned;

}

