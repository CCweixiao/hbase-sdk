package com.github.CCweixiao.hbase.sdk.common.type;

import com.alibaba.fastjson2.JSON;
import com.github.CCweixiao.hbase.sdk.common.type.handler.*;
import com.github.CCweixiao.hbase.sdk.common.type.handler.ext.HexBytes;
import com.github.CCweixiao.hbase.sdk.common.type.handler.ext.HexBytesHandler;

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
    StringType("string", String.class, new StringHandler()),
    CharType("char", Character.class, new CharHandler()),
    ShortType("short", Short.class, new ShortHandler()),
    LongType("long", Long.class, new LongHandler()),
    MapType("map", Map.class, new JsonHandler()),
    ListType("list", List.class, new JsonHandler()),
    IntegerType("int", Integer.class, new IntegerHandler()),
    FloatType("float", Float.class, new FloatHandler()),
    DoubleType("double", Double.class, new DoubleHandler()),
    DateType("date", Date.class, new DateHandler()),
    ByteType("byte", Byte.class, new ByteHandler()),
    BoolType("bool", Boolean.class, new BooleanHandler()),
    BigIntegerType("bigint", BigInteger.class, new BigIntegerHandler()),
    BigDecimalType("bigdecimal", BigDecimal.class, new BigDecimalHandler()),
    HexByteType("hex", HexBytes.class, new HexBytesHandler()),
    JsonType("json", JSON.class, new JsonHandler()),
    EnumType("enum", Enum.class, new EnumHandler());

    private final String typeName;
    private final Class<?> typeClass;
    private final TypeHandler<?> typeHandler;

    ColumnType(String typeName, Class<?> typeClass, AbstractTypeHandler<?> typeHandler) {
        this.typeName = typeName;
        this.typeClass = typeClass;
        this.typeHandler = typeHandler;
    }

    public String getTypeName() {
        return typeName;
    }

    public Class<?> getTypeClass() {
        return typeClass;
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
}
