package com.github.CCweixiao.hbase.sdk.common.reflect;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseColumn;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseRowKey;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseTable;
import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseMetaDataException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandlerFactory;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author leojie 2022/11/20 10:50
 */
public class ReflectFactory {

    private volatile static Map<Class<?>, HBaseTableMeta> hBaseTableMetaCacheMap;

    public static HBaseTableMeta getHBaseTableMeta(Class<?> clazz) {
        if (hBaseTableMetaCacheMap == null || !hBaseTableMetaCacheMap.containsKey(clazz)) {
            synchronized (ReflectFactory.class) {
                if (hBaseTableMetaCacheMap == null || !hBaseTableMetaCacheMap.containsKey(clazz)) {
                    if (hBaseTableMetaCacheMap == null) {
                        hBaseTableMetaCacheMap = new HashMap<>(2);
                    }
                    if (!hBaseTableMetaCacheMap.containsKey(clazz)) {
                        String tableName = getTableName(clazz);
                        MethodAccess methodAccess = MethodAccess.get(clazz);
                        Map<String, Method> allMethodsMap = getAllMethodsMap(clazz);
                        FieldAccess fieldAccess = FieldAccess.get(clazz);
                        Field[] fields = fieldAccess.getFields();
                        List<Field> rowFields = new ArrayList<>(1);
                        List<FieldStruct> fieldStructList = new LinkedList<>();
                        Method getMethod;
                        Method setMethod;
                        for (Field field : fields) {
                            if (isNotGeneralProperty(field)) {
                                continue;
                            }
                            FieldStruct fieldStruct = new FieldStruct();
                            fieldStruct.setType(field.getType());
                            // 标识一个字段是否是row key
                            boolean fieldIsRowKey = field.isAnnotationPresent(HBaseRowKey.class);
                            if (fieldIsRowKey) {
                                fieldStruct.setRowKey(true);
                                rowFields.add(field);
                            } else {
                                fieldStruct.setRowKey(false);
                            }
                            // 解析col name
                            String[] familyAndQualifierArr = getHBaseColumnName(clazz, field).split(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR);
                            fieldStruct.setFamily(familyAndQualifierArr[0]);
                            fieldStruct.setQualifier(familyAndQualifierArr[1]);
                            fieldStruct.setFamilyAndQualifier(familyAndQualifierArr[0] +
                                    HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR + familyAndQualifierArr[1]);
                            // setter
                            String setterMethodName = getSetterMethodName(field);
                            setMethod = allMethodsMap.getOrDefault(setterMethodName, null);
                            if (isNotGeneralSetterMethod(setMethod)) {
                                throw new HBaseOperationsException("Please assign a standard setter method for property: " + field.getName() + ".");
                            }
                            fieldStruct.setSetterMethodName(setterMethodName);
                            int setterMethodIndex = methodAccess.getIndex(setterMethodName);
                            fieldStruct.setSetterMethodIndex(setterMethodIndex);
                            // getter
                            String getterMethodName = getGetterMethodName(field);
                            getMethod = allMethodsMap.getOrDefault(getterMethodName, null);
                            if (isNotGeneralGetterMethod(getMethod)) {
                                throw new HBaseMetaDataException("Please assign a standard getter method for property: " + field.getName() + ".");
                            }
                            fieldStruct.setGetterMethodName(getterMethodName);
                            int getMethodIndex = methodAccess.getIndex(getterMethodName);
                            fieldStruct.setGetterMethodIndex(getMethodIndex);
                            // 设置列数据转换器
                            fieldStruct.setTypeHandler(TypeHandlerFactory.findTypeHandler(field.getType()));

                            if (fieldIsRowKey) {
                                fieldStructList.add(0, fieldStruct);
                            } else {
                                fieldStructList.add(fieldStruct);
                            }
                        }
                        if (rowFields.isEmpty()) {
                            throw new HBaseMetaDataException("Please assign one row key for the model class " + clazz.getName());
                        }
                        if (rowFields.size() > 1) {
                            throw new HBaseMetaDataException("The numbers of row key > 1 in the model class " + clazz.getName());
                        }
                        HBaseTableMeta tableMeta = new HBaseTableMeta();
                        tableMeta.setTableName(tableName);
                        tableMeta.setMethodAccess(methodAccess);
                        tableMeta.setFieldAccess(fieldAccess);
                        tableMeta.setFieldStructList(fieldStructList);
                        hBaseTableMetaCacheMap.put(clazz, tableMeta);
                    }
                }
            }
        }
        return hBaseTableMetaCacheMap.get(clazz);
    }

    /**
     * 反射获取HBase表名，如果没有{@link HBaseTable}注解，则拿数据实体映射类的 _ 分割的类名作为表名，
     * 如果命名空间没有指定，则使用默认的命名空间：default
     *
     * @param clazz 数据实体映射类的类型
     * @return HBase的表名，包含有命名空间名称
     */
    private static String getTableName(Class<?> clazz) {
        String className = clazz.getSimpleName();
        String tableName = StrUtil.underscoreName(className);
        if (clazz.isAnnotationPresent(HBaseTable.class)) {
            HBaseTable table = clazz.getAnnotation(HBaseTable.class);
            if (StrUtil.isNotBlank(table.tableName())) {
                tableName = table.tableName();
            }
            return StrUtil.isBlank(table.namespaceName()) ? "default:" + tableName : table.namespaceName() + ":" + tableName;
        } else {
            return "default:" + tableName;
        }
    }

    private static String getTableDefaultFamilyName(Class<?> clazz) {
        String defaultFamilyName = "";
        if (clazz.isAnnotationPresent(HBaseTable.class)) {
            defaultFamilyName = clazz.getAnnotation(HBaseTable.class).defaultFamilyName();
        }
        if (defaultFamilyName.contains(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR)) {
            throw new HBaseOperationsException("The family name cannot contain ':'.");
        }
        return defaultFamilyName;
    }


    /**
     * 创建HBase数据保存时的字段名，如果数据实体类字段没有被{@link HBaseColumn}注解修饰，
     * 则 _ 分割驼峰命名法的默认方式将作为字段名。
     * 如果没有指定全局列簇名，则必须为每一个字段指定列簇名，每一个字段注解中指定的列簇名的优先级最高。
     * 通过上述注解，你可以设置字段名大写或者小写
     *
     * @param clazz 数据实体映射类的类型
     * @param field 字段名
     * @return 最终的字段名，例如：info:name, info:is_vip ......
     */
    private static String getHBaseColumnName(Class<?> clazz, Field field) {
        String fieldName = StrUtil.underscoreName(field.getName());
        String columnFamilyName = getTableDefaultFamilyName(clazz);
        if (field.isAnnotationPresent(HBaseColumn.class)) {
            HBaseColumn column = field.getAnnotation(HBaseColumn.class);
            if (StrUtil.isNotBlank(column.columnName())) {
                fieldName = column.columnName();
            }
            if (column.toUpperCase()) {
                fieldName = fieldName.toUpperCase();
            }
            if (StrUtil.isNotBlank(column.familyName())) {
                columnFamilyName = column.familyName();
            }
        }
        if (columnFamilyName.contains(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR)) {
            throw new HBaseOperationsException("The column family name cannot contain ':'.");
        }
        if (fieldName.contains(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR)) {
            throw new HBaseOperationsException("The column qualifier name cannot contain ':'.");
        }
        if (StrUtil.isNotBlank(columnFamilyName)) {
            return columnFamilyName + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR + fieldName;
        } else {
            throw new HBaseOperationsException("the family should be assigned in the field of " + field.getName());
        }
    }

    private static boolean booleanType(Class<?> type) {
        return type == boolean.class || type == Boolean.class;
    }

    /**
     * 获取某一字段的get方法名称
     *
     * @param field 字段obj
     * @return getter function name
     */
    private static String getGetterMethodName(Field field) {
        String fieldName = field.getName();
        StringBuilder sb = StrUtil.builder();
        if (booleanType(field.getType()) && fieldName.startsWith("is")) {
            //如果字段是boolean类型的
            sb.append("is");
            sb.append(fieldName.substring(2).substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(2).substring(1));

        } else {
            sb.append("get");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
        }
        return sb.toString();
    }

    /**
     * 获取某一字段的set方法名称
     *
     * @param field 字段obj
     * @return setter function name
     */
    private static String getSetterMethodName(Field field) {
        String fieldName = field.getName();
        StringBuilder sb = StrUtil.builder();
        if (booleanType(field.getType()) && fieldName.startsWith("is")) {
            //如果字段是boolean类型的
            sb.append("set");
            sb.append(fieldName.substring(2).substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(2).substring(1));

        } else {
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
        }
        return sb.toString();
    }

    /**
     * 判断一个字段类型是不是一个常规属性，比如：static final 修饰的字段暂时不属于一个常规字段
     *
     * @param field 字段类型
     * @return 是否是一个常规属性
     */
    private static boolean isNotGeneralProperty(Field field) {
        if (field == null) {
            return true;
        }
        return Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers());
    }

    /**
     * 判断一个属性所属方法是否是一个常规的getter方法
     * 依据是：属性getter方法不能用private修饰
     * 且该属性方法的返回值不能是void的
     *
     * @param method method function
     * @return 是否是一个常规的getter方法
     */
    private static boolean isNotGeneralGetterMethod(Method method) {
        if (method == null) {
            return true;
        }
        return Modifier.isPrivate(method.getModifiers()) || method.getReturnType() == void.class;
    }

    /**
     * 判断一个属性所属方法是否是一个常规的setter方法
     * 依据是：属性setter方法不能用private修饰
     * 且该属性方法的返回值是void的
     *
     * @param method method function
     * @return 是否是一个常规的setter方法
     */
    private static boolean isNotGeneralSetterMethod(Method method) {
        if (method == null) {
            return true;
        }
        return Modifier.isPrivate(method.getModifiers()) || method.getReturnType() != void.class;
    }

    private static Map<String, Method> getAllMethodsMap(Class<?> clazz) {
        Method[] methods = getAllMethods(clazz);
        return Arrays.stream(methods).collect(Collectors.toMap(Method::getName, field -> field));
    }

    /**
     * 获取某一个类所有的字段，包括其所有父类的字段
     *
     * @param clazz 类的定义类对象
     * @return 字段数组
     */
    private static Method[] getAllMethods(Class<?> clazz) {
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
    private static Method[] getSuperClassMethods(Method[] methods, Class<?> clazz) {
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
}
