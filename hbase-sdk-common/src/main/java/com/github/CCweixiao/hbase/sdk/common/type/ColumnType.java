package com.github.CCweixiao.hbase.sdk.common.type;

import com.github.CCweixiao.hbase.sdk.common.type.handler.*;
import com.github.CCweixiao.hbase.sdk.common.type.handler.ext.HexBytes;
import com.github.CCweixiao.hbase.sdk.common.type.handler.ext.HexBytesHandler;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    EnumType("enum", Enum.class, new EnumHandler());

    private final String typeName;
    private final Class<?> typeClass;
    private final AbstractTypeHandler<?> typeHandler;

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

    public AbstractTypeHandler<?> getTypeHandler() {
        return typeHandler;
    }
}
