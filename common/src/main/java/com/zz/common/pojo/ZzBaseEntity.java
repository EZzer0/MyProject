package com.zz.common.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZzBaseEntity {

    public static final String COL_ZZ_CREATE_TIME = "zz_create_time";
    public static final String COL_ZZ_MODIFIED_TIME = "zz_modified_time";
    public static final String COL_ZZ_CREATE_ACCOUNT_ID = "zz_create_account_id";
    public static final String COL_ZZ_MODIFIED_ACCOUNT_ID = "zz_modified_account_id";
    public static final String COL_ZZ_DELETED = "zz_deleted";
    /**
     * 创建时间
     */
    @TableField(value = "`zz_create_time`", fill = FieldFill.INSERT)
    private Date zzCreateTime;
    /**
     * 修改时间
     */
    @TableField(value = "`zz_modified_time`", fill = FieldFill.UPDATE)
    private Date zzModifiedTime;
    /**
     * 创建人
     */
    @TableField(value = "`zz_create_account_id`", fill = FieldFill.INSERT)
    private Long zzCreateAccountId;
    /**
     * 修改人
     */
    @TableField(value = "`zz_modified_account_id`", fill = FieldFill.UPDATE)
    private Long zzModifiedAccountId;
    /**
     * 逻辑删除标识(0、否 1、是)
     */
    @TableField(value = "`zz_deleted`", fill = FieldFill.INSERT)
    private Byte zzDeleted;

}
