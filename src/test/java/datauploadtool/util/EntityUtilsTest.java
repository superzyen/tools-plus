package datauploadtool.util;

import datauploadtool.DataUploadToolApplication;
import datauploadtool.mysql.common.FilePathConstants;
import datauploadtool.mysql.common.RegistedEntityTable;
import datauploadtool.mysql.common.RegistedToolTable;
import datauploadtool.mysql.common.TableRecord;
import datauploadtool.mysql.excel.ExcelFactory;
import datauploadtool.mysql.excel.IExcelTemplate;
import datauploadtool.mysql.mapper.CommMapper;
import datauploadtool.mysql.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = DataUploadToolApplication.class)
class EntityUtilsTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void main() throws Exception {
        //初始化工具表单
        RegistedToolTable.initializeTable();
        //初始化实体类表单
        RegistedEntityTable.initializeTable();


    }
}