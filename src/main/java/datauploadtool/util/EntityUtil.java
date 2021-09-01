package datauploadtool.util;

import com.google.common.collect.Maps;
import datauploadtool.uploadtool.common.RegistedEntityTable;
import datauploadtool.uploadtool.common.RegistedToolTable;
import datauploadtool.uploadtool.common.TableRegister;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public class EntityUtil {

    /**
     * 将得到对象以object的形式返回，最后以反射的形式进行数据库的操作
     */
    public static Object transform(String tableName, Map<String, String> fieldMap) throws IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {
        Map<String, Class> entityMap = TableRegister.getInstance().getEntityMap();
        if (entityMap.containsKey(tableName)) {
            Class clazz = entityMap.get(tableName);
            Method m = clazz.getDeclaredMethod("setValue", new Class[]{String.class, String.class});
            Object obj = clazz.newInstance();
            for (String key : fieldMap.keySet()) {
                m.invoke(obj, key, fieldMap.get(key));
            }
            return obj;
        } else {
            log.error("无法为该实体类进行转换, tableName:{}", tableName);
            System.out.println("无法为该数据库表进行上传更新的操作，请与super_zyen联系");
            return null;
        }
    }

    /**
     * 通过反射得到转换后的class 对象，得到实体类的sql 语句，后直接执行sql
     */
    public static String getSqlStr(String tableName, Map<String, String> fieldMap) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object obj = EntityUtil.transform(tableName, fieldMap);
        Class clazz = obj.getClass();
        Method m = clazz.getDeclaredMethod("getSqlString", new Class[]{});
        return String.valueOf(m.invoke(obj));
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        //初始化工具表单
        RegistedToolTable.initializeTable();
        //初始化实体类表单
        RegistedEntityTable.initializeTable();
        Map<String, String> tempMap = Maps.newHashMap();
        tempMap.put("id", "123");
        String sql = EntityUtil.getSqlStr("member", tempMap);
        System.out.println(sql);
    }

}
