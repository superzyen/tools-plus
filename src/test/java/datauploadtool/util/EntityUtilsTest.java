package datauploadtool.util;

import com.google.common.collect.Maps;
import datauploadtool.DataUploadToolApplication;
import datauploadtool.mysql.mapper.CommMapper;
import datauploadtool.mysql.common.RegistedEntityTable;
import datauploadtool.mysql.common.RegistedToolTable;
import datauploadtool.mysql.common.TableRecord;
import datauploadtool.mysql.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, String> tempMap = Maps.newHashMap();
        tempMap.put("type_id", "9");
        tempMap.put("title", "zyentool");
        tempMap.put("status", "2");
        tempMap.put("type_path", " ");
        tempMap.put("content", " ");
        tempMap.put("author", "zyen");
        tempMap.put("source", "1");
        tempMap.put("is_out", "1");
        tempMap.put("out_url", " ");
        tempMap.put("sort", "1");
        tempMap.put("is_recommend", "0");
        tempMap.put("source_type", "9");
        tempMap.put("create_id", "123321");

        TableRecord tableRecord = new TableRecord();
        tableRecord.setFields(tempMap.keySet().stream().collect(Collectors.toList()));
        tableRecord.setRecord(tempMap);
        String sql = EntityUtils.analyse("news").getSqlStr(tableRecord);
        System.out.println(sql);
        CommMapper mapper = BeanUtils.getBean(CommMapper.class);
        mapper.insert(sql);
    }
}