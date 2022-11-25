package com.github.CCweixiao.hbase.sdk.hql.rowkeytextfunc;

/**
 * Row Key Function 名称枚举
 *
 * @author leojie 2020/12/6 1:32 下午
 */
public enum RowKeyFunctionNameEnum {
    /**
     * RowKeyFunction
     */
    INT_KEY("intkey"),
    STRING_KEY("stringkey"),
    LONG_KEY("longkey"),
    HEX_KEY("hexkey")
    ;

    private final String functionName;

    RowKeyFunctionNameEnum(String functionName){
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }
}
