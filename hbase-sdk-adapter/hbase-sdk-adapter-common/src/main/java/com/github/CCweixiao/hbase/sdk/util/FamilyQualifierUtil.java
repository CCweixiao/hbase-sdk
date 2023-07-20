package com.github.CCweixiao.hbase.sdk.util;

import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

import java.util.List;

/**
 * @author leojie 2023/7/20 19:50
 */
public class FamilyQualifierUtil {
    /**
     * 判断列簇名满足条件，需要筛选的字段列表未指定
     *
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @return 最终数据
     */
    public static boolean familyNameOnly(String familyName, List<String> qualifiers) {
        return StringUtil.isNotBlank(familyName) && (qualifiers == null || qualifiers.isEmpty());
    }

    /**
     * 判断列簇名和需要筛选的字段列表同时成立
     *
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @return 最终数据
     */
    public static boolean familyAndColumnNames(String familyName, List<String> qualifiers) {
        return StringUtil.isNotBlank(familyName) && (qualifiers != null && !qualifiers.isEmpty());
    }
}
