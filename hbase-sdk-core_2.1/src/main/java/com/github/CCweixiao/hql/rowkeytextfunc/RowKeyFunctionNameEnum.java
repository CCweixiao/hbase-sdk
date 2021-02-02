package com.github.CCweixiao.hql.rowkeytextfunc;

/**
 * Row Key Function 名称枚举
 *
 * @author leojie 2020/12/6 1:32 下午
 */
public enum RowKeyFunctionNameEnum {
    INT_KEY("intkey"),
    STRING_KEY("stringkey"),
    LONG_KEY("longkey"),
    HEX_KEY("hexkey")
    ;

    private String functionName;

    RowKeyFunctionNameEnum(String functionName){
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
