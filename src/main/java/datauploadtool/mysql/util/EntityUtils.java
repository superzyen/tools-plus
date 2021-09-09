package datauploadtool.mysql.util;

import com.google.common.collect.Maps;
import datauploadtool.annotation.TableField;
import datauploadtool.exception.NoTypeMapException;
import datauploadtool.mysql.common.EntityRegister;
import datauploadtool.mysql.common.TableRecord;
import datauploadtool.util.DateUtils;
import datauploadtool.util.StringUtils;
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
import java.util.stream.Collectors;

@Slf4j
public class EntityUtils {

    private EntityUtils() {
    }

    //<>-><tableField,fieldClass>
    private static final Map<Long, Map<String, Class>> threadTypeMap = Maps.newHashMap();
    //<>-><tableField,fieldName>
    private static final Map<Long, Map<String, String>> threadNameMap = Maps.newHashMap();

    /**
     * 将得到对象以object的形式返回，最后以反射的形式进行数据库的操作
     */
    public static Object transform(String tableName, Map<String, String> fieldMap) throws IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException, ParseException {
        Map<String, Class> entityMap = EntityRegister.getInstance().getEntityMap();
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

    /**
     * 数据与实体类的映射都基于此方法
     * 先调用该方法进行解析之后，再进行其他操作
     *
     * @return
     * @author wenzy
     * @date 2021/9/9 10:02
     */
    public static EntityMapping analyse(String tableName) {
        Map<String, Class> entityMap = EntityRegister.getInstance().getEntityMap();
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
        return new EntityMapping(tableName);
    }


    /**
     * 基于分析方法之后的映射对象操作{@linkplain EntityUtils#analyse(String)}
     *
     * @author wenzy
     * @date 2021/9/9 10:26
     */
    public static class EntityMapping {

        private String tableName;

        protected EntityMapping() {
        }

        protected EntityMapping(String tableName) {
            this.tableName = tableName;
        }

        /**
         * 通过反射得到转换后的class 对象，得到实体类的sql 语句，后直接执行sql
         */
        public String getSqlStr(TableRecord tableRecord) throws Exception {
            List<String> fieldList = tableRecord.getFields();
            Map<Integer, Map<String, String>> recordsMap = tableRecord.getRecords();

            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO ");
            builder.append(this.tableName);
            builder.append(" (");
            for (String field : fieldList) {
                builder.append(field);
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(") VALUES ");
            for (Integer index : recordsMap.keySet()) {
                Map<String, String> record = recordsMap.get(index);
                builder.append(oneRecord(record));
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            return builder.toString();
        }

        private String oneRecord(Map<String, String> recordMap) throws Exception {
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

        /**
         * 获取实体类所有的table field的集合{@linkplain datauploadtool.annotation.TableField}
         *
         * @author wenzy
         * @date 2021/9/9 15:14
         */
        public List<String> getTableFields() {
            return threadNameMap.get(Thread.currentThread().getId()).keySet().stream().collect(Collectors.toList());
        }
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


}
