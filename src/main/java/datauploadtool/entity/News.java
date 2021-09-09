package datauploadtool.entity;

import datauploadtool.annotation.TableField;
import datauploadtool.uploadtool.common.IEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class News  implements IEntity {

    private static final String TABLE_NAME = "news";

    @TableField("id")
    private Integer id;

    @TableField("type_id")
    private Integer typeId;

    @TableField("title")
    private String title;

    @TableField("status")
    private Integer status;

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
