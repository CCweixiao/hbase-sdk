package com.github.CCweixiao;

import com.github.CCwexiao.dsl.config.HBaseSQLRuntimeSetting;

/**
 * @author leojie 2020/11/28 9:09 下午
 */
public interface HBaseSQLRuntimeSettingAware {
    /**
     * 获取HBaseSQLRuntimeSetting
     * @return HBaseSQLRuntimeSetting
     */
     HBaseSQLRuntimeSetting getHBaseSQLRuntimeSetting();

    /**
     * 设置HBaseSQLRuntimeSetting
     * @param runtimeSetting HBaseSQLRuntimeSetting
     */
     void setHBaseSQLRuntimeSetting(HBaseSQLRuntimeSetting runtimeSetting);
}
