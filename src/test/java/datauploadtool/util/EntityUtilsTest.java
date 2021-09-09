package datauploadtool.util;

import com.google.common.collect.Maps;
import datauploadtool.DataUploadToolApplication;
import datauploadtool.mapper.MemberMapper;
import datauploadtool.uploadtool.common.RegistedEntityTable;
import datauploadtool.uploadtool.common.RegistedToolTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = DataUploadToolApplication.class)
class EntityUtilsTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void main() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ParseException {
        //初始化工具表单
        RegistedToolTable.initializeTable();
        //初始化实体类表单
        RegistedEntityTable.initializeTable();
        Map<String, String> tempMap = Maps.newHashMap();
        tempMap.put("type_id", "9");
        tempMap.put("title", "zyentool");
        tempMap.put("status", "2");
        Object object=EntityUtils.transform("news",tempMap);
        MemberMapper mapper = BeanUtils.getBean(MemberMapper.class);
        mapper.insert(object);
    }
}