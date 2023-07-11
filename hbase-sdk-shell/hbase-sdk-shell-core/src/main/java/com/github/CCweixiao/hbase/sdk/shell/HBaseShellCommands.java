package com.github.CCweixiao.hbase.sdk.shell;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author leojie 2023/7/11 22:19
 */
public class HBaseShellCommands {
    private volatile static Set<String> commandsSet;
    private HBaseShellCommands () {

    }
    public static Set<String> getAllCommands() throws IOException {
        if (commandsSet == null) {
            synchronized (HBaseShellCommands.class) {
                if (commandsSet == null) {
                    URL commandFilesUrl = HBaseShellCommands.class.getClassLoader().getResource("hbase-ruby/shell/commands/");
                    if (commandFilesUrl == null) {
                        throw new IOException("The command files path is null!");
                    }
                    String commandFilePath = commandFilesUrl.getPath();
                    File commandFile = new File(commandFilePath);
                    if (!commandFile.exists()) {
                        throw new IOException("The command files path is not exists!");
                    }
                    String[] files = commandFile.list();
                    if (files == null) {
                        throw new IOException("The command files is null!");
                    }
                    Set<String> sortedSet = new TreeSet<>();
                    for (String file : files) {
                        if (file.endsWith(".rb")){
                            sortedSet.add(file.substring(0, file.lastIndexOf(".rb")));
                        }
                    }
                    commandsSet = sortedSet;
                }
            }
        }
        return commandsSet;
    }

    public static List<String> searchCommand(String subCommand) {
        List<String> matchCommands = new ArrayList<>();

        try {
            Set<String> allCommands = getAllCommands();
            for (String command : allCommands) {
                if (command.startsWith(subCommand)) {
                    matchCommands.add(command);
                }
            }
        } catch (IOException e) {
            return matchCommands;
        }
        return matchCommands;
    }
}
