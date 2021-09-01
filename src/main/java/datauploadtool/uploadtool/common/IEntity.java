package datauploadtool.uploadtool.common;

public interface IEntity {

    static final String TABLE_NAME = "entity";

    String getTableName();

    void setValue(String fieldStr, String fieldValue);

    String getSqlString();

}
