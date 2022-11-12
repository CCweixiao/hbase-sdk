package com.github.CCwexiao.hbase.sdk.dsl.config;


import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
import com.github.CCwexiao.hbase.sdk.dsl.type.TypeHandler;
import com.github.CCwexiao.hbase.sdk.dsl.type.TypeHandlerHolder;
import com.github.CCwexiao.hbase.sdk.dsl.type.TypeHandlers;
import com.github.CCwexiao.hbase.sdk.dsl.util.ClassUtil;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;

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
    private TypeHandler typeHandler;


    public void init(TypeHandlers typeHandlers) {
        Util.checkEmptyFamilyName(family);
        Util.checkEmptyQualifierName(qualifier);
        Util.checkEmptyCTypeName(typeName);

        type = ClassUtil.forName(typeName);
        Util.checkNull(type);

        if (StrUtil.isBlank(typeHandlerName)) {
            typeHandler = typeHandlers.findDefaultTypeHandler(type);
            typeHandlerName = typeHandler.getClass().getName();
        } else {
            typeHandler = TypeHandlerHolder.findTypeHandler(typeHandlerName);
        }

        Util.checkNull(typeHandlerName);
        Util.checkNull(typeHandler);

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
