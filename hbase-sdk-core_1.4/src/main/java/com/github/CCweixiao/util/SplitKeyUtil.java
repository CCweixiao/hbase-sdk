package com.github.CCweixiao.util;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leojie 2021/2/2 5:53 下午
 */
public class SplitKeyUtil {
    public static byte[][] getSplitKeyBytes(String[] splitKeys) {
        final List<byte[]> bytes = Arrays.stream(splitKeys).distinct().map(Bytes::toBytes).collect(Collectors.toList());
        byte[][] splitKeyBytes = new byte[bytes.size()][];
        for (int i = 0; i < bytes.size(); i++) {
            splitKeyBytes[i] = bytes.get(i);
        }
        return splitKeyBytes;
    }

    public static void main(String[] args) {
        final byte[][] splitKeyBytes = getSplitKeyBytes(new String[]{"1", "1", "2"});
        System.out.println(Arrays.deepToString(splitKeyBytes));
    }
}
