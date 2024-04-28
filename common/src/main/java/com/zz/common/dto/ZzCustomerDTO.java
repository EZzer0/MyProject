package com.zz.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zz.common.pojo.ZzBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Schema(description = "ZzCustomer 数据传输对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZzCustomerDTO extends ZzBaseDTO {

    @Schema(description = "客户主键")
    @JsonProperty("zzCustomerId")
    private Long zzCustomerId;

    @Schema(description = "真实姓名")
    @JsonProperty("zzRealName")
    private String zzRealName;

    @Schema(description = "性别")
    @JsonProperty("zzSex")
    private String zzSex;

    @Schema(description = "年龄")
    @JsonProperty("zzAge")
    private Long zzAge;

    @Schema(description = "邮箱")
    @JsonProperty("zzEmail")
    private String zzEmail;

    @Schema(description = "电话号码")
    @JsonProperty("zzPhone")
    private String zzPhone;

    @Schema(description = "地址")
    @JsonProperty("zzAddress")
    private String zzAddress;

    @Schema(description = "密码")
    @JsonProperty("zzPassword")
    private String zzPassword;

    @Schema(description = "账户余额")
    @JsonProperty("zzBalance")
    private BigDecimal zzBalance;


}

