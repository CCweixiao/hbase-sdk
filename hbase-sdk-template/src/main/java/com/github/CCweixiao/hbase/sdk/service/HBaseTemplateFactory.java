package com.github.CCweixiao.hbase.sdk.service;

import com.github.CCweixiao.hbase.sdk.common.HBaseAdminOperations;
import com.github.CCweixiao.hbase.sdk.common.HBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkUnsupportedVersionException;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * @author leojie 2022/10/22 14:28
 */
public class HBaseTemplateFactory {

    public static HBaseAdminOperations getHBaseAdminOperations(String hbaseAdapterVersion, Properties prop) {
        if (StrUtil.isBlank(hbaseAdapterVersion)) {
            throw new HBaseSdkUnsupportedVersionException("The version of hbase client adapter is not empty.");
        }
        String className = HBaseClientAdapterVersionEnum.getHBaseAdminTemplateAdapterClassNameByVersion(hbaseAdapterVersion);
        if (StrUtil.isBlank(className)) {
            throw new HBaseSdkUnsupportedVersionException(String.format("The version %s of hbase client adapter passed in is not in the support list %s",
                    hbaseAdapterVersion, HBaseClientAdapterVersionEnum.toShowString()));
        }
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getDeclaredConstructor(Properties.class);
            return (HBaseAdminOperations) constructor.newInstance(prop);
        } catch (ClassNotFoundException e) {
            throw new HBaseSdkUnsupportedVersionException("The class " + className + " of hbase client adapter is not found.");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 InstantiationException e) {
            throw new HBaseSdkUnsupportedVersionException(e);
        }
    }

    public static HBaseTableOperations getHBaseTableOperations(String hbaseAdapterVersion, Properties prop) {
        if (StrUtil.isBlank(hbaseAdapterVersion)) {
            throw new HBaseSdkUnsupportedVersionException("The version of hbase client adapter is not empty.");
        }
        String className = HBaseClientAdapterVersionEnum.getHBaseTemplateAdapterClassNameByVersion(hbaseAdapterVersion);
        if (StrUtil.isBlank(className)) {
            throw new HBaseSdkUnsupportedVersionException(String.format("The version %s of hbase client adapter passed in is not in the support list %s",
                    hbaseAdapterVersion, HBaseClientAdapterVersionEnum.toShowString()));
        }
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getDeclaredConstructor(Properties.class);
            return (HBaseTableOperations) constructor.newInstance(prop);
        } catch (ClassNotFoundException e) {
            throw new HBaseSdkUnsupportedVersionException("The class " + className + " of hbase client adapter is not found.");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 InstantiationException e) {
            throw new HBaseSdkUnsupportedVersionException(e);
        }
    }
}
