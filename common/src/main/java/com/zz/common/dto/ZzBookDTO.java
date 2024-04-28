package com.zz.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zz.common.pojo.ZzBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Schema(description = "ZzBook 数据传输对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZzBookDTO extends ZzBaseDTO {

    @Schema(description = "书籍主键")
    @JsonProperty("zzBookId")
    private Long zzBookId;

    @Schema(description = "书籍标题")
    @JsonProperty("zzTitle")
    private String zzTitle;

    @Schema(description = "作者")
    @JsonProperty("zzAuthor")
    private String zzAuthor;

    @Schema(description = "图像URL")
    @JsonProperty("zzUrl")
    private String zzUrl;

    @Schema(description = "价格")
    @JsonProperty("zzPrice")
    private BigDecimal zzPrice;

    @Schema(description = "库存数量")
    @JsonProperty("zzInventoryCount")
    private Integer zzInventoryCount;


}

