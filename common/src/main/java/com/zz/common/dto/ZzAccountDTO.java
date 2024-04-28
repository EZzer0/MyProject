package com.zz.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zz.common.pojo.ZzBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Schema(description = "ZzAccount 数据传输对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class ZzAccountDTO extends ZzBaseDTO {

    @Schema(description = "账户主键")
    @JsonProperty("zzAccountId")
    private Long zzAccountId;

    @Schema(description = "账户名")
    @JsonProperty("zzUsername")
    private String zzUsername;

    @Schema(description = "密码")
    @JsonProperty("zzPassword")
    private String zzPassword;

    @Schema(description = "加密盐")
    @JsonProperty("zzSalt")
    private String zzSalt;

    @Schema(description = "真实姓名")
    @JsonProperty("zzRealName")
    private String zzRealName;

    @Schema(description = "性别（M: 男, F: 女, N: 不明）")
    @JsonProperty("zzSex")
    private String zzSex;

    @Schema(description = "邮箱")
    @JsonProperty("zzEmail")
    private String zzEmail;

    @Schema(description = "手机号")
    @JsonProperty("zzPhone")
    private String zzPhone;

    @Schema(description = "角色集合")
    @JsonProperty("zzRoles")
    private List<ZzRoleDTO> zzRoles;


}

