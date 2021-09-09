package datauploadtool.util;

import com.google.common.collect.Maps;
import datauploadtool.annotation.TableField;
import datauploadtool.exception.NoTypeMapException;
import datauploadtool.uploadtool.common.TableRecord;
import datauploadtool.uploadtool.common.TableRegister;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class EntityUtils {

    private EntityUtils() {
    }

    private static final Map<Long, Map<String, Class>> threadTypeMap = Maps.newHashMap();
    private static final Map<Long, Map<String, String>> threadNameMap = Maps.newHashMap();

    /**
     * 将得到对象以object的形式返回，最后以反射的形式进行数据库的操作
     */
    public static Object transform(String tableName, Map<String, String> fieldMap) throws IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException, ParseException {
        Map<String, Class> entityMap = TableRegister.getInstance().getEntityMap();
        if (entityMap.containsKey(tableName)) {
            Class clazz = entityMap.get(tableName);
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object obj = constructor.newInstance();

            //获取字段集合，判断字段的类型
            Field[] fields = clazz.getDeclaredFields();
            Map<String, Class> typeMap = Maps.newHashMap();
            Map<String, String> nameMap = Maps.newHashMap();
            for (Field field : fields) {
                if (null == field.getAnnotation(TableField.class)) {
                    continue;
                }
                String tableField = field.getAnnotation(TableField.class).value();
                typeMap.put(tableField, field.getType());
                nameMap.put(tableField, field.getName());
            }

            String prefix = "set";
            //通过set方法为字段赋值
            for (String key : fieldMap.keySet()) {
                if (typeMap.containsKey(key)) {
                    String fieldName = nameMap.get(key);
                    Class typeCla = typeMap.get(key);
                    String fieldVal = fieldMap.get(key);
                    String methodStr = prefix + StringUtils.UpperFirstChar(fieldName);
                    Method method = clazz.getDeclaredMethod(methodStr, new Class[]{typeCla});
                    method.invoke(obj, matchType(fieldVal, typeCla));
                }
            }
            return obj;
        } else {
            log.error("无法为该实体类进行转换, tableName:{}", tableName);
            System.out.println("无法为该数据库表进行上传更新的操作，请与super_zyen联系");
            return null;
        }
    }

    public static EntityUtils analyse(String tableName) {
        Map<String, Class> entityMap = TableRegister.getInstance().getEntityMap();
        if (entityMap.containsKey(tableName)) {
            Class clazz = entityMap.get(tableName);

            //获取字段集合，判断字段的类型
            Field[] fields = clazz.getDeclaredFields();
            Map<String, Class> typeMap = Maps.newHashMap();
            Map<String, String> nameMap = Maps.newHashMap();
            for (Field field : fields) {
                if (null == field.getAnnotation(TableField.class)) {
                    continue;
                }
                String tableField = field.getAnnotation(TableField.class).value();
                typeMap.put(tableField, field.getType());
                nameMap.put(tableField, field.getName());
            }
            setThreadTypeMap(typeMap);
            setThreadNameeMap(nameMap);
        }
        return new EntityUtils();
    }

    public static Object matchType(String val, Class type) throws ParseException {
        if (type == Integer.class) {
            return Integer.valueOf(val);
        } else if (type == String.class) {
            return val;
        } else if (type == Date.class) {
            return DateUtils.toDate(val);
        } else {
            log.error("无法识别的实体类字段类型, type:{}", type);
            System.out.println("实体类字段转换错误，请与super_zyen联系");
            return null;
        }
    }

    public static void setThreadTypeMap(Map<String, Class> typeMap) {
        Objects.requireNonNull(typeMap);
        Long id = Thread.currentThread().getId();
        threadTypeMap.put(id, typeMap);
    }

    public static Map<String, Class> getThreadTypeMap() {
        Long id = Thread.currentThread().getId();
        if (threadTypeMap.containsKey(id)) {
            return threadTypeMap.get(id);
        }
        return null;
    }

    public static void setThreadNameeMap(Map<String, String> nameMap) {
        Objects.requireNonNull(nameMap);
        Long id = Thread.currentThread().getId();
        threadNameMap.put(id, nameMap);
    }

    public static Map<String, String> getThreadNameMap() {
        Long id = Thread.currentThread().getId();
        if (threadNameMap.containsKey(id)) {
            return threadNameMap.get(id);
        }
        return null;
    }

    /**
     * 通过反射得到转换后的class 对象，得到实体类的sql 语句，后直接执行sql
     */
    public static String getSqlStr(String tableName, TableRecord tableRecord) {
        List<String> fieldList = tableRecord.getFields();

        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(tableName);
        builder.append(" (");
        for (String field : fieldList) {
            builder.append(field);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(") VALUES ");
        
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        return builder.toString();
    }

    private static String oneRecord(Map<String, String> recordMap) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        Long id = Thread.currentThread().getId();
        Map<String, Class> typeMap = null;
        if (threadTypeMap.containsKey(id)) {
            typeMap = threadTypeMap.get(id);
        } else {
            throw new NoTypeMapException("threadTypeMap 为空，请先分析字段的类型");
        }
        for (String name : recordMap.keySet()) {
            String val = recordMap.get(name);
            Class type = null;
            if (typeMap.containsKey(name)) {
                type = typeMap.get(name);
            }
            if (null != val && val != "") {
                if (type == Integer.class) {
                    builder.append(val);
                } else if (type == String.class) {
                    builder.append("'" + val + "'");
                } else if (type == Date.class) {
                    builder.append("'" + val + "'");
                } else {
                    log.error("无法识别的实体类字段类型, type:{}", type);
                    System.out.println("实体类字段转换错误，请与super_zyen联系");
                    return null;
                }
                builder.append(",");
            } else {
                builder.append("null");
                builder.append(",");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        return builder.toString();
    }

}
