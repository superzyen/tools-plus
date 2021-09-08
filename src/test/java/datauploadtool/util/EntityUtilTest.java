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
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = DataUploadToolApplication.class)
class EntityUtilTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void main() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        //初始化工具表单
        RegistedToolTable.initializeTable();
        //初始化实体类表单
        RegistedEntityTable.initializeTable();
        Map<String, String> tempMap = Maps.newHashMap();
        tempMap.put("name", "tooltest");
        tempMap.put("password", "tooltest");
        tempMap.put("register_time", "2021-09-07 17:42:00");
        String sql = EntityUtil.getSqlStr("member", tempMap);
        MemberMapper mapper = BeanUtil.getBean(MemberMapper.class);
        mapper.insert(sql);
    }
}