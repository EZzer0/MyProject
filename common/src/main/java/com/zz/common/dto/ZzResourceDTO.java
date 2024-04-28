package com.zz.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zz.common.pojo.ZzBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Schema(description = "ZzResource 数据传输对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZzResourceDTO extends ZzBaseDTO {

    @Schema(description = "资源主键")
    @JsonProperty("zzResourceId")
    private Long zzResourceId;

    @Schema(description = "资源名称")
    @JsonProperty("zzResourceName")
    private String zzResourceName;

    @Schema(description = "父资源ID，NULL表示顶级资源")
    @JsonProperty("zzParentId")
    private Long zzParentId;

    @Schema(description = "链接")
    @JsonProperty("zzLink")
    private String zzLink;

    @Schema(description = "显示顺序")
    @JsonProperty("zzOrder")
    private Integer zzOrder;

    @Schema(description = "资源类型(0:目录, 1:菜单, 2:按钮)")
    @JsonProperty("zzResourceType")
    private Integer zzResourceType;

    @Schema(description = "是否为拥有该资源")
    @JsonProperty("zzIsOwned")
    private boolean zzIsOwned;


}

