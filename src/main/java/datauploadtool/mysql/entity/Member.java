package datauploadtool.mysql.entity;

import datauploadtool.annotation.TableField;
import datauploadtool.mysql.common.IEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class Member implements IEntity {

    private static final String TABLE_NAME = "member";

    /**
     * 自增字段
     */
    @TableField("id")
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("password")
    private String password;

    @TableField("grade")
    private Integer grade;

    @TableField("grade_value")
    private Integer gradeValue;

    @TableField("integral")
    private Integer integral;

    @TableField("phone")
    private String phone;

    @TableField("status")
    private Integer status;

    @TableField("seller_community_shop_id")
    private Integer sellerCommunityShopId;

    @TableField("register_time")
    private Date registerTime;

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
