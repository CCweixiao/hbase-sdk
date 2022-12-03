package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCweixiao.hbase.sdk.common.HBaseFuncNotSupportedException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

/**
 * @author leojie 2022/12/3 13:11
 */
public enum RowKeyFunction {
    /**
     * row key
     */
    convert_to_int("convert_to_int", new ConvertIntRowKeyFunc()),
    convert_to_long("convert_to_long", new ConvertLongRowKeyFunc()),
    md5_prefix("md5_prefix", new Md5PrefixRowKeyFunc()),
    md5("md5", new Md5RowKeyFunc()),
    reverse("reverse", new ReverseRowKeyFunc());

    private final String funcName;
    private final RowKeyFunc<?> rowKeyFunc;

    RowKeyFunction(String funcName, RowKeyFunc<?> rowKeyFunc) {
        this.funcName = funcName;
        this.rowKeyFunc = rowKeyFunc;
    }

    public String getFuncName() {
        return funcName;
    }

    public RowKeyFunc<?> getRowKeyFunc() {
        return rowKeyFunc;
    }

    public static RowKeyFunction findRowKeyFunc(String funcName) {
        MyAssert.checkArgument(StringUtil.isNotBlank(funcName), "The function name must not ne empty");
        funcName = funcName.trim().toLowerCase();
        for (RowKeyFunction function : RowKeyFunction.values()) {
            if (funcName.equals(function.getFuncName())) {
                return function;
            }
        }
        throw new HBaseFuncNotSupportedException("The function " + funcName + " not supported.");
    }
}
