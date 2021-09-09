package datauploadtool.mysql.entity;

import datauploadtool.annotation.TableField;
import datauploadtool.mysql.common.IEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class News implements IEntity {

    private static final String TABLE_NAME = "news";

    @TableField("id")
    private Integer id;

    @TableField("type_id")
    private Integer typeId;

    @TableField("type_path")
    private String typePath;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("author")
    private String author;

    @TableField("source")
    private Integer source;

    @TableField("is_out")
    private Integer isOut;

    @TableField("out_url")
    private String outUrl;

    @TableField("status")
    private Integer status;

    @TableField("sort")
    private Integer sort;

    @TableField("is_recommend")
    private Integer isRecommend;

    @TableField("source_type")
    private Integer sourceType;

    @TableField("create_id")
    private Integer createId;


    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
