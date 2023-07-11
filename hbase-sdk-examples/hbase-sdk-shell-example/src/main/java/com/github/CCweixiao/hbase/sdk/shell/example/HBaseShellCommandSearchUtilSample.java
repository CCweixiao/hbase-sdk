package com.github.CCweixiao.hbase.sdk.shell.example;

import com.github.CCweixiao.hbase.sdk.shell.HBaseShellCommands;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author leojie 2023/7/11 23:03
 */
public class HBaseShellCommandSearchUtilSample {
    public static void main(String[] args) {
        Set<String> allCommands = null;
        try {
            allCommands = HBaseShellCommands.getAllCommands();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> li = HBaseShellCommands.searchCommand("create_nam");
        System.out.println(allCommands);
    }
}
