package com.github.CCwexiao.dsl.config;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;
import com.github.CCwexiao.dsl.literal.LiteralInterpreter;
import com.github.CCwexiao.dsl.literal.interpreter.*;
import com.github.CCwexiao.dsl.util.ClassUtil;
import com.github.CCwexiao.dsl.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/11/28 11:07 上午
 */
public abstract class HBaseSQLRuntimeSetting {
    /**
     * scan 缓存大小
     */
    private int scanCachingSize = 20;

    /**
     * delete batch size
     */
    private int deleteBatchSize = 50;

    /**
     * 确定扫描是否可以使用查询的限制来计算缓存大小
     */
    private boolean intelligentScanSize;

    /**
     * row key函数列表
     */
    private final Map<String, RowKeyTextFunc> buildInRowKeyTextFuncCache = new HashMap<>();

    private final Map<Class<?>, LiteralInterpreter> literalInterpreterCache = new HashMap<>();
    private final Map<Class<?>, LiteralInterpreter> buildInLiteralInterpreterCache = new HashMap<>();

    public HBaseSQLRuntimeSetting() {
        List<LiteralInterpreter> buildInLiteralInterpreterList = new ArrayList<>();
        buildInLiteralInterpreterList.add(new BooleanInterpreter());
        buildInLiteralInterpreterList.add(new ByteInterpreter());
        buildInLiteralInterpreterList.add(new CharInterpreter());
        buildInLiteralInterpreterList.add(new DateInterpreter());
        buildInLiteralInterpreterList.add(new DoubleInterpreter());
        buildInLiteralInterpreterList.add(new FloatInterpreter());
        buildInLiteralInterpreterList.add(new IntegerInterpreter());
        buildInLiteralInterpreterList.add(new LongInterpreter());
        buildInLiteralInterpreterList.add(new ShortInterpreter());
        buildInLiteralInterpreterList.add(new StringInterpreter());
        buildInLiteralInterpreterList.add(new HexBytesInterpreter());
        for (LiteralInterpreter interpreter : buildInLiteralInterpreterList) {
            Class<?> type = ClassUtil.tryConvertToBoxClass(interpreter.getTypeCanInterpret());
            buildInLiteralInterpreterCache.put(type, interpreter);
        }
        List<RowKeyTextFunc> buildInRowKeyTextFuncList = buildAllRowKeyTextFuncList();
        if (buildInRowKeyTextFuncList != null && !buildInRowKeyTextFuncList.isEmpty()) {
            for (RowKeyTextFunc func : buildInRowKeyTextFuncList) {
                buildInRowKeyTextFuncCache.put(func.funcName(), func);
            }
        }
    }

    /**
     * 构造所有的rowKey转换函数
     */
   protected abstract List<RowKeyTextFunc> buildAllRowKeyTextFuncList();


    public RowKeyTextFunc findRowKeyTextFunc(String funcName) {
        if (buildInRowKeyTextFuncCache.containsKey(funcName)) {
            return buildInRowKeyTextFuncCache.get(funcName);
        }

        throw new HBaseOperationsException("can not find func for " + funcName);
    }

    public Object interpret(Class type, String literalValue){
        Util.checkNull(type);
        Util.checkNull(literalValue);
        Class<?> tempType = ClassUtil.tryConvertToBoxClass(type);

        if(literalInterpreterCache.containsKey(tempType)){
            return literalInterpreterCache.get(tempType).interpret(literalValue);
        }

        if(buildInLiteralInterpreterCache.containsKey(tempType)){
            return buildInLiteralInterpreterCache.get(tempType).interpret(literalValue);
        }

        Object result = null;
        if(tempType.isEnum()){
            result = Enum.valueOf(type, literalValue);
        }
        Util.checkNull(result);

        return result;
    }

    public int getScanCachingSize() {
        return scanCachingSize;
    }

    public void setScanCachingSize(int scanCachingSize) {
        this.scanCachingSize = scanCachingSize;
    }

    public int getDeleteBatchSize() {
        return deleteBatchSize;
    }

    public void setDeleteBatchSize(int deleteBatchSize) {
        this.deleteBatchSize = deleteBatchSize;
    }

    public boolean isIntelligentScanSize() {
        return intelligentScanSize;
    }

    public void setIntelligentScanSize(boolean intelligentScanSize) {
        this.intelligentScanSize = intelligentScanSize;
    }

    public void setLiteralInterpreterCache(List<LiteralInterpreter> literalInterpreterList){
        if (literalInterpreterList != null) {
            for (LiteralInterpreter interpreter : literalInterpreterList) {
                Class<?> type = ClassUtil.tryConvertToBoxClass(interpreter.getTypeCanInterpret());
                literalInterpreterCache.put(type, interpreter);
            }
        }
    }
}
