package com.github.CCweixiao.hql.config;

import com.github.CCweixiao.hql.rowkeytextfunc.HexBytesRowKeyTextFunc;
import com.github.CCweixiao.hql.rowkeytextfunc.IntRowKeyTextFunc;
import com.github.CCweixiao.hql.rowkeytextfunc.LongRowKeyTextFunc;
import com.github.CCweixiao.hql.rowkeytextfunc.StringRowKeyTextFunc;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;
import com.github.CCwexiao.dsl.config.HBaseSQLRuntimeSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2020/11/28 1:01 下午
 */
public class DefaultHBaseSQLRuntimeSetting extends HBaseSQLRuntimeSetting {
    public DefaultHBaseSQLRuntimeSetting(){
        super();
    }

    @Override
    protected List<RowKeyTextFunc> buildAllRowKeyTextFuncList() {
        List<RowKeyTextFunc> rowKeyTextFuncList = new ArrayList<>(4);
        rowKeyTextFuncList.add(new IntRowKeyTextFunc());
        rowKeyTextFuncList.add(new LongRowKeyTextFunc());
        rowKeyTextFuncList.add(new StringRowKeyTextFunc());
        rowKeyTextFuncList.add(new HexBytesRowKeyTextFunc());
        return rowKeyTextFuncList;
    }
}
