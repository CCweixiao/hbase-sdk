package com.github.CCweixiao.hbase.sdk.common.type;

import com.alibaba.fastjson2.JSON;
import com.github.CCweixiao.hbase.sdk.common.type.handler.*;
import com.github.CCweixiao.hbase.sdk.common.type.handler.ext.HexBytes;
import com.github.CCweixiao.hbase.sdk.common.type.handler.ext.HexBytesHandler;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2022/12/2 23:58
 */
public enum ColumnType {
    /**
     * schema type
     */
    StringType("string", String.class, String.class, new StringHandler()),
    CharType("char", Character.class, char.class, new CharHandler()),
    ShortType("short", Short.class, short.class, new ShortHandler()),
    LongType("long", Long.class, long.class, new LongHandler()),
    MapType("map", Map.class, Map.class, new JsonHandler()),
    ListType("list", List.class, List.class, new JsonHandler()),
    IntegerType("int", Integer.class, int.class, new IntegerHandler()),
    FloatType("float", Float.class, float.class, new FloatHandler()),
    DoubleType("double", Double.class, double.class, new DoubleHandler()),
    DateType("date", Date.class, Date.class, new DateHandler()),
    ByteType("byte", Byte.class, byte.class, new ByteHandler()),
    BoolType("bool", Boolean.class, boolean.class, new BooleanHandler()),
    BigIntegerType("bigint", BigInteger.class, BigInteger.class, new BigIntegerHandler()),
    BigDecimalType("bigdecimal", BigDecimal.class, BigDecimal.class, new BigDecimalHandler()),
    HexByteType("hex", HexBytes.class, HexBytes.class, new HexBytesHandler()),
    JsonType("json", JSON.class, JSON.class, new JsonHandler()),
    EnumType("enum", Enum.class, Enum.class, new EnumHandler());

    private final String typeName;
    private final Class<?> typeClass;

    private final Class<?> orTypeClass;
    private final TypeHandler<?> typeHandler;

    ColumnType(String typeName, Class<?> typeClass, Class<?> orTypeClass, AbstractTypeHandler<?> typeHandler) {
        this.typeName = typeName;
        this.typeClass = typeClass;
        this.orTypeClass = orTypeClass;
        this.typeHandler = typeHandler;
    }

    public String getTypeName() {
        return typeName;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    public Class<?> getOrTypeClass() {
        return orTypeClass;
    }

    public TypeHandler<?> getTypeHandler() {
        return typeHandler;
    }

    public static TypeHandler<?> findTypeHandler(Class<?> typeClass) {
        if (typeClass == null) {
            return null;
        }
        if (typeClass.isEnum()) {
            return EnumType.getTypeHandler();
        }
        for (ColumnType columnType : ColumnType.values()) {
            if (columnType.getTypeClass() == typeClass) {
                return columnType.getTypeHandler();
            }
            if (columnType.getOrTypeClass() == typeClass) {
                return columnType.getTypeHandler();
            }
        }
        return JsonType.getTypeHandler();
    }

    public static Object toObject(Class<?> type, byte[] bytes) {
        TypeHandler<?> typeHandler = findTypeHandler(type);
        return typeHandler.toObject(type, bytes);
    }

    public static String toString(byte[] bytes) {
        return toObject(String.class, bytes).toString();
    }

    public static String toStrFromBuffer(ByteBuffer buffer) {
        Object val = StringType.getTypeHandler().toObject(String.class, buffer);
        if (val == null) {
            return "";
        }
        return val.toString();
    }

    public static ByteBuffer toByteBuffer(Object val) {
        TypeHandler<?> typeHandler = findTypeHandler(val.getClass());
        return typeHandler.toByteBuffer(val);
    }

    public static ByteBuffer toStrByteBuffer(Object val) {
        TypeHandler<?> typeHandler = findTypeHandler(val.getClass());
        String strVal = typeHandler.toString(val);
        return toByteBufferFromStr(strVal);
    }

    public static ByteBuffer toByteBufferFromStr(String val) {
        TypeHandler<?> typeHandler = findTypeHandler(String.class);
        return typeHandler.toByteBuffer(val);
    }

    public static ColumnType getColumnType(String typeName) {
        if (StringUtil.isBlank(typeName)) {
            return null;
        }
        for (ColumnType value : ColumnType.values()) {
            if (typeName.equalsIgnoreCase(value.getTypeName())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unsupported type " + typeName);
    }
}
