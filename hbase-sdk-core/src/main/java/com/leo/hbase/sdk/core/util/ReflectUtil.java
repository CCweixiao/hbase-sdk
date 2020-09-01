package com.leo.hbase.sdk.core.util;


import com.leo.hbase.sdk.core.annotation.HBaseColumn;
import com.leo.hbase.sdk.core.annotation.HBaseTable;
import com.leo.hbase.sdk.core.exception.HBaseOperationsException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>反射工具类</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public class ReflectUtil {
    /**
     * 获取某一个类的所有字段，转成map
     *
     * @param clazz 目标类的定义字段
     * @return 所有字段的map
     */
    public static Map<String, Method> getAllMethodsMap(Class<?> clazz) {
        Method[] methods = getAllMethods(clazz);
        return Arrays.stream(methods).collect(Collectors.toMap(Method::getName, field -> field));
    }

    /**
     * 获取某一个类所有的字段，包括其所有父类的字段
     *
     * @param clazz 类的定义类对象
     * @return 字段数组
     */
    public static Method[] getAllMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz.equals(Object.class)) {
            return methods;
        }
        Method[] tableSuperMethods = superClazz.getDeclaredMethods();
        Method[] superMethods = new Method[methods.length + tableSuperMethods.length];
        System.arraycopy(methods, 0, superMethods, 0, methods.length);
        System.arraycopy(tableSuperMethods, 0, superMethods, methods.length, tableSuperMethods.length);
        return getSuperClassMethods(superMethods, superClazz);
    }

    /**
     * 递归获取所有的父类方法
     *
     * @param methods 方法列表
     * @param clazz   类的定义类对象
     * @return 方法数组
     */
    public static Method[] getSuperClassMethods(Method[] methods, Class<?> clazz) {
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz.equals(Object.class)) {
            return methods;
        }
        Method[] superMethods = superClazz.getDeclaredMethods();
        Method[] c = new Method[methods.length + superMethods.length];
        System.arraycopy(methods, 0, c, 0, methods.length);
        System.arraycopy(superMethods, 0, c, methods.length, superMethods.length);
        getSuperClassMethods(c, superClazz);
        return c;
    }

    /**
     * 获取某一个类的所有字段，转成map
     *
     * @param clazz 目标类的定义字段
     * @return 所有字段的map
     */
    public static Map<String, Field> getAllFieldsMap(Class<?> clazz) {
        Field[] fields = getAllFields(clazz);
        return Arrays.stream(fields).collect(Collectors.toMap(Field::getName, field -> field));
    }

    /**
     * 获取某一个类所有的字段，包括其所有父类的字段
     *
     * @param clazz 类的定义类对象
     * @return 字段数组
     */
    public static Field[] getAllFields(Class<?> clazz) {
        Field[] tableFields = clazz.getDeclaredFields();
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz.equals(Object.class)) {
            return tableFields;
        }
        Field[] tableSuperFields = superClazz.getDeclaredFields();
        Field[] superFields = new Field[tableFields.length + tableSuperFields.length];
        System.arraycopy(tableFields, 0, superFields, 0, tableFields.length);
        System.arraycopy(tableSuperFields, 0, superFields, tableFields.length, tableSuperFields.length);
        return getSuperClassFields(superFields, superClazz);
    }

    /**
     * 递归获取所有的父类字段
     *
     * @param fields 父类字段
     * @param clazz  类的定义类对象
     * @return 字段数组
     */
    public static Field[] getSuperClassFields(Field[] fields, Class<?> clazz) {
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz.equals(Object.class)) {
            return fields;
        }
        Field[] superFields = superClazz.getDeclaredFields();

        Field[] c = new Field[fields.length + superFields.length];
        System.arraycopy(fields, 0, c, 0, fields.length);
        System.arraycopy(superFields, 0, c, fields.length, superFields.length);
        getSuperClassFields(c, superClazz);
        return c;
    }

    public static String getUniqueTableFamily(Class<?> clazz) {
        if (clazz.isAnnotationPresent(HBaseTable.class)) {
            return clazz.getAnnotation(HBaseTable.class).uniqueFamily();
        }
        return "";
    }

    /**
     * 反射获取HBase表名，如果没有{@link HBaseTable}注解，则拿数据实体映射类的 _ 分割的类名作为表名，
     * 如果命名空间没有指定，则使用默认的命名空间：default
     *
     * @param clazz 数据实体映射类的类类型
     * @return HBase的表名
     */
    public static String getHBaseTableName(Class<?> clazz) {
        String className = clazz.getSimpleName();
        String tableName = FieldOrTableNameUtil.underscoreName(className);
        if (clazz.isAnnotationPresent(HBaseTable.class)) {
            HBaseTable table = clazz.getAnnotation(HBaseTable.class);
            if (!"".equals(table.name())) {
                tableName = table.name();
            }
            return "".equals(table.schema()) ? "default:" + tableName : table.schema() + ":" + tableName;
        } else {
            return "default:" + tableName;
        }
    }


    /**
     * 创建HBase数据保存时的字段名，如果数据实体类字段没有被{@link HBaseColumn}注解修饰，
     * 则 _ 分割驼峰命名法的默认方式将作为字段名。
     * 如果没有指定全局列簇名，则必须为每一个字段指定列簇名，每一个字段注解中指定的列簇名的优先级最高。
     * 通过上述注解，你可以设置字段名大写或者小写
     *
     * @param uniqueFamily 唯一列簇
     * @param field        字段名
     * @return 最终的字段名，例如：info:name, info:is_vip ......
     */
    public static String getHBaseColumnName(String uniqueFamily, Field field) {
        String fieldName = FieldOrTableNameUtil.underscoreName(field.getName());
        String columnFamily = null;
        if (field.isAnnotationPresent(HBaseColumn.class)) {
            HBaseColumn column = field.getAnnotation(HBaseColumn.class);
            if (!"".equals(column.name())) {
                fieldName = column.name();
            }
            if (column.toUpperCase()) {
                fieldName = fieldName.toUpperCase();
            }
            if (!"".equals(column.family())) {
                columnFamily = column.family();
            }
        }

        if (columnFamily != null && !"".equals(columnFamily)) {
            return columnFamily + ":" + fieldName;
        } else {
            if (uniqueFamily == null || "".equals(uniqueFamily)) {
                throw new HBaseOperationsException("the family should be assigned in the field of " + field.getName());
            } else {
                return uniqueFamily + ":" + fieldName;
            }
        }
    }

    /**
     * 判断一个方法是一个常规的getter方法
     *
     * @param method method function
     * @return 是否是一个常规的getter方法
     */
    public static boolean isNotGeneralGetterMethod(Method method) {
        if (method == null) {
            return true;
        }
        return !Modifier.isPublic(method.getModifiers()) || method.getReturnType() == void.class;
    }

    /**
     * 判断一个方法是一个常规的setter方法
     *
     * @param method method function
     * @return 是否是一个常规的setter方法
     */
    public static boolean isNotGeneralSetterMethod(Method method) {
        if (method == null) {
            return true;
        }
        return !Modifier.isPublic(method.getModifiers()) || method.getReturnType() != void.class;
    }

    /**
     * 判断一个字段类型是不是一个常规属性，比如：static final 修饰的字段暂时不属于一个常规字段
     *
     * @param field 字段类型
     * @return 是否是一个常规属性
     */
    public static boolean isNotGeneralProperty(Field field) {
        if (field == null) {
            return true;
        }
        return Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers());
    }

    /**
     * 获取某一字段的get方法名称
     *
     * @param field 字段obj
     * @return getter function name
     */
    public static String getGetterName(Field field) {
        String propertyName = field.getName();
        StringBuilder sb = new StringBuilder();
        if (booleanType(field.getType()) && propertyName.startsWith("is")) {
            //如果字段是boolean类型的
            sb.append("is");
            sb.append(propertyName.substring(2).substring(0, 1).toUpperCase());
            sb.append(propertyName.substring(2).substring(1));

        } else {
            sb.append("get");
            sb.append(propertyName.substring(0, 1).toUpperCase());
            sb.append(propertyName.substring(1));
        }
        return sb.toString();
    }

    /**
     * 获取某一字段的set方法名称
     *
     * @param field 字段obj
     * @return setter function name
     */
    public static String getSetterName(Field field) {
        String propertyName = field.getName();
        StringBuilder sb = new StringBuilder();
        if (booleanType(field.getType()) && propertyName.startsWith("is")) {
            //如果字段是boolean类型的
            sb.append("set");
            sb.append(propertyName.substring(2).substring(0, 1).toUpperCase());
            sb.append(propertyName.substring(2).substring(1));

        } else {
            sb.append("set");
            sb.append(propertyName.substring(0, 1).toUpperCase());
            sb.append(propertyName.substring(1));
        }
        return sb.toString();
    }

    private static boolean booleanType(Class<?> type) {
        return type == boolean.class || type == Boolean.class;
    }

}
