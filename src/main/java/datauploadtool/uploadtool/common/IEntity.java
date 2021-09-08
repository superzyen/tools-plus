package datauploadtool.uploadtool.common;

import java.text.ParseException;

public interface IEntity {

    static final String TABLE_NAME = "entity";

    String getTableName();

    void setValue(String fieldStr, String fieldValue) throws ParseException;

    String getSqlString();

}
