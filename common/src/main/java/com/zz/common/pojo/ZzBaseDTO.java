package com.zz.common.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;


@Data
@Schema(description = "ZzAccount 数据传输对象")
public class ZzBaseDTO {

    @Schema(description = "创建时间")
    @JsonProperty("zzCreateTime")
    private Date zzCreateTime;

    @Schema(description = "修改时间")
    @JsonProperty("zzModifiedTime")
    private Date zzModifiedTime;

    @Schema(description = "创建人")
    @JsonProperty("zzCreateAccountId")
    private Long zzCreateAccountId;

    @Schema(description = "修改人")
    @JsonProperty("zzModifiedAccountId")
    private Long zzModifiedAccountId;

    @Schema(description = "逻辑删除标识(0、否 1、是)")
    @JsonProperty("zzDeleted")
    private Byte zzDeleted;

}
