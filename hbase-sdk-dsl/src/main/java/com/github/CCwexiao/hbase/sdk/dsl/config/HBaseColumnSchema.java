package com.github.CCwexiao.hbase.sdk.dsl.config;


import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandlerFactory;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.ClassUtil;
import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

/**
 * @author leojie 2020/11/27 10:45 下午
 */
public class HBaseColumnSchema {
    /**
     * HBase 列簇
     */
    private String family;

    /**
     * HBase字段名
     */
    private String qualifier;

    /**
     * HBase字段类型，java type
     */
    private String typeName;

    /**
     * type handler name
     */
    private String typeHandlerName;

    /**
     * java type
     */
    private Class<?> type;

    /**
     * type handler 的实现
     */
    private AbstractTypeHandler typeHandler;


    public void init() {
        ObjUtil.checkEmptyFamilyName(family);
        ObjUtil.checkEmptyQualifierName(qualifier);
        ObjUtil.checkEmptyCTypeName(typeName);

        type = ClassUtil.forName(typeName);
        MyAssert.notNull(type);

        if (StringUtil.isBlank(typeHandlerName)) {
            typeHandler = TypeHandlerFactory.findTypeHandler(type);
            typeHandlerName = typeHandler.getClass().getName();
        } else {
            typeHandler = TypeHandlerFactory.findTypeHandler(typeHandlerName);
        }

        MyAssert.notNull(typeHandlerName);
        MyAssert.notNull(typeHandler);

    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeHandlerName() {
        return typeHandlerName;
    }

    public void setTypeHandlerName(String typeHandlerName) {
        this.typeHandlerName = typeHandlerName;
    }

    public Class<?> getType() {
        return type;
    }

    public TypeHandler getTypeHandler() {
        return typeHandler;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[family=" + family + ",");
        sb.append("qualifier=" + qualifier + ",");
        sb.append("typeName=" + typeName + ",");
        sb.append("typeHandlerName=" + typeHandlerName + "]");
        return sb.toString();
    }
}
