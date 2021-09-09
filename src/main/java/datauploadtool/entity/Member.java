package datauploadtool.entity;

import datauploadtool.uploadtool.common.IEntity;
import datauploadtool.util.DateUtils;
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
    private Integer id;

    private String name;

    private String password;

    private Integer grade;

    private Integer gradeValue;

    private Integer integral;

    private String phone;

    private Integer status;

    private Integer sellerCommunityShopId;

    private Date registerTime;

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
