package datauploadtool.entity;

import com.alibaba.druid.util.StringUtils;
import datauploadtool.uploadtool.common.IEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Member implements IEntity {

    private static final String TABLE_NAME = "member";

    /**
     * 自增字段
     */
    private Integer id;
    private final String idStr = "id";

    private String name;
    private final String nameStr = "name";

    private String password;
    private final String passwordStr = "password";

    private Integer grade;
    private final String gradeStr = "grade";

    private Integer gradeValue;
    private final String gradeValueStr = "grade_value";

    private Integer integral;
    private final String integralStr = "integral";

    private String phone;
    private final String phoneStr = "phone";

    private Integer status;
    private final String statusStr = "status";

    private Integer sellerCommunityShopId;
    private final String sellerCommunityShopIdStr = "seller_community_shop_id";

    @Override
    public void setValue(String fieldStr, String fieldValue) {
        if (StringUtils.equals(fieldStr, this.idStr)) {
            this.id = Integer.valueOf(String.valueOf(fieldValue));
        } else if (StringUtils.equals(fieldStr, this.nameStr)) {
            this.name = fieldValue;
        } else if (StringUtils.equals(fieldStr, this.passwordStr)) {
            this.password = fieldValue;
        } else if (StringUtils.equals(fieldStr, this.gradeStr)) {
            this.grade = Integer.valueOf(fieldValue);
        } else if (StringUtils.equals(fieldStr, this.gradeValueStr)) {
            this.gradeValue = Integer.valueOf(fieldValue);
        } else if (StringUtils.equals(fieldStr, this.integralStr)) {
            this.integral = Integer.valueOf(fieldValue);
        } else if (StringUtils.equals(fieldStr, this.phoneStr)) {
            this.phone = fieldValue;
        } else if (StringUtils.equals(fieldStr, this.statusStr)) {
            this.status = Integer.valueOf(fieldValue);
        } else if (StringUtils.equals(fieldStr, this.sellerCommunityShopIdStr)) {
            this.sellerCommunityShopId = Integer.valueOf(fieldValue);
        }
    }

    @Override
    public String getSqlString() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(TABLE_NAME);
        builder.append("(");
        if (null != this.id) {
            builder.append(this.idStr);
            builder.append(",");
        }
        if (null != this.name) {
            builder.append(this.nameStr);
            builder.append(",");
        }
        if (null != this.password) {
            builder.append(this.passwordStr);
            builder.append(",");
        }
        if (null != this.grade) {
            builder.append(this.gradeStr);
            builder.append(",");
        }
        if (null != this.gradeValue) {
            builder.append(this.gradeValueStr);
            builder.append(",");
        }
        if (null != this.integral) {
            builder.append(this.integralStr);
            builder.append(",");
        }
        if (null != this.phone) {
            builder.append(this.phoneStr);
            builder.append(",");
        }
        if (null != this.status) {
            builder.append(this.statusStr);
            builder.append(",");
        }
        if (null != this.sellerCommunityShopId) {
            builder.append(this.sellerCommunityShopIdStr);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(") VALUES (");
        if (null != this.id) {
            builder.append(this.getId());
            builder.append(",");
        }
        if (null != this.name) {
            builder.append("'" + this.getName() + "'");
            builder.append(",");
        }
        if (null != this.password) {
            builder.append("'" + this.getPassword() + "'");
            builder.append(",");
        }
        if (null != this.grade) {
            builder.append(this.getGrade());
            builder.append(",");
        }
        if (null != this.gradeValue) {
            builder.append(this.getGradeValue());
            builder.append(",");
        }
        if (null != this.integral) {
            builder.append(this.getIntegral());
            builder.append(",");
        }
        if (null != this.phone) {
            builder.append("'" + this.getPhone() + "'");
            builder.append(",");
        }
        if (null != this.status) {
            builder.append(this.getStatus());
            builder.append(",");
        }
        if (null != this.sellerCommunityShopId) {
            builder.append(this.getSellerCommunityShopId());
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        return builder.toString();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
