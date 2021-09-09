package datauploadtool.mysql.excel;

import datauploadtool.mysql.common.TableRecord;

import java.io.IOException;

public interface IExcelTemplate {
    void info();

    void create(String filePath) throws IOException;

    TableRecord read(String filePath) throws IOException;
}
