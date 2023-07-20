package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseMetaDataException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.reflect.FieldStruct;
import com.github.CCweixiao.hbase.sdk.common.reflect.HBaseTableMeta;
import com.github.CCweixiao.hbase.sdk.common.reflect.ReflectFactory;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.List;
import java.util.Map;

/**
 * @author leojie 2023/7/20 19:32
 */
public interface IHBaseTablePutAdapter {

    default Put buildPutCondition(String rowKey, Map<String, Object> data) {
        if (StringUtil.isBlank(rowKey)) {
            throw new IllegalArgumentException("rowKey must not be empty.");
        }
        Put put = new Put(Bytes.toBytes(rowKey));
        data.forEach((fieldName, fieldValue) -> {
            String[] familyQualifierArr = fieldName.split(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR);
            TypeHandler<?> fieldTypeHandler = ColumnType.findTypeHandler(fieldValue.getClass());
            put.addColumn(Bytes.toBytes(familyQualifierArr[0]), Bytes.toBytes(familyQualifierArr[1]),
                    ColumnType.StringType.getTypeHandler().toBytes(fieldTypeHandler.toString(fieldValue)));
        });
        return put;
    }

    default <T> Put buildPut(T t) throws HBaseMetaDataException {
        Class<?> clazz = t.getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        List<FieldStruct> fieldStructList = tableMeta.getFieldStructList();
        FieldStruct rowFieldStruct = fieldStructList.get(0);
        if (!rowFieldStruct.isRowKey()) {
            throw new HBaseMetaDataException("The first field is not row key, please check hbase table mata data.");
        }
        Object value = tableMeta.getMethodAccess().invoke(t, rowFieldStruct.getGetterMethodIndex());
        MyAssert.checkArgument(value != null, "The value of row key must not be null.");
        Put put = new Put(rowFieldStruct.getTypeHandler().toBytes(rowFieldStruct.getType(), value));

        fieldStructList.forEach(fieldStruct -> {
            if (!fieldStruct.isRowKey()) {
                Object fieldValue = tableMeta.getMethodAccess().invoke(t, fieldStruct.getGetterMethodIndex());
                put.addColumn(Bytes.toBytes(fieldStruct.getFamily()),
                        Bytes.toBytes(fieldStruct.getQualifier()),
                        fieldStruct.getTypeHandler().toBytes(fieldStruct.getType(), fieldValue));
            }
        });
        return put;
    }
}
