package com.github.CCweixiao.util;

/**
 * @author leojie 2020/10/7 9:48 下午
 */
public enum SplitGoEnum {
    /**
     * 预分区策略，十六进制
     */
    HEX_STRING_SPLIT("HexStringSplit"),
    DECIMAL_STRING_SPLIT("DecimalStringSplit");

    private final String splitGo;

    SplitGoEnum(String splitGo) {
        this.splitGo = splitGo;
    }

    public String getSplitGo() {
        return splitGo;
    }

    public static SplitGoEnum getSplitGoEnum(String splitGo) {
        for (SplitGoEnum splitGoEnum : SplitGoEnum.values()) {
            if (splitGoEnum.splitGo.equals(splitGo)) {
                return splitGoEnum;
            }
        }
        return null;
    }

}
