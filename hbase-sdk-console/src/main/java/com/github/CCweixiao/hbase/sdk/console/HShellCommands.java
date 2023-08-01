package com.github.CCweixiao.hbase.sdk.console;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseShellSessionEnvInitException;
import com.github.CCweixiao.hbase.sdk.shell.HBaseShellCommands;
import com.github.CCweixiao.hbase.sdk.shell.HBaseShellSession;
import com.github.CCweixiao.hbase.sdk.shell.HBaseShellSessionManager;
import com.github.CCweixiao.hbase.sdk.shell.Result;
import org.jline.console.CmdDesc;
import org.jline.console.CommandInput;
import org.jline.console.CommandMethods;
import org.jline.console.Printer;
import org.jline.reader.impl.completer.SystemCompleter;
import org.jline.utils.InfoCmp;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author leojie 2023/7/29 21:15
 */
public class HShellCommands extends BaseCommands {
    private final Map<String, CommandMethods> commandExecute = new HashMap<>();
    private final Map<String, List<String>> commandInfo = new HashMap<>();
    private final Map<String, String> aliasCommand = new HashMap<>();


    public HShellCommands(Printer printer) {
        super(printer);
        try {
            Set<String> allCommands = HBaseShellCommands.getAllCommands();
            allCommands.add("desc");
            for (String commandName : allCommands) {
                commandExecute.put(commandName, new CommandMethods(this::execShellCommand, this::defaultCompleter));
            }
        } catch (IOException e) {
            throw new HBaseShellSessionEnvInitException(e);
        }

        commandExecute.put("ruby_exec", new CommandMethods(this::rubyExec, this::defaultCompleter));
        commandExecute.put("clear", new CommandMethods(this::clear, this::defaultCompleter));
        commandInfo.put("clear", Collections.singletonList("clear all input."));
        registerCommands(commandExecute);
    }

    private HBaseShellSession loadShellSession() {
        return HBaseShellSessionManager.getHBaseShellSession(HClusterContext.getInstance().getCurrentClusterProperties());
    }

    public Map<String, String> commandAliases() {
        return aliasCommand;
    }

    public Set<String> commandNames() {
        return commandExecute.keySet();
    }

    public List<String> commandInfo(String command) {
        return commandInfo.get(command(command));
    }

    public boolean hasCommand(String command) {
        return commandExecute.containsKey(command) || aliasCommand.containsKey(command);
    }

    private String command(String name) {
        if (commandExecute.containsKey(name)) {
            return name;
        }
        return aliasCommand.get(name);
    }

    public SystemCompleter compileCompleters() {
        SystemCompleter out = new SystemCompleter();
        for (Map.Entry<String, CommandMethods> entry : commandExecute.entrySet()) {
            out.add(entry.getKey(), entry.getValue().compileCompleter().apply(entry.getKey()));
        }
        out.addAliases(aliasCommand);
        return out;
    }

    public Object invoke(CommandSession session, String command, Object... args) throws Exception {
        return commandExecute.get(command(command)).execute().apply(new CommandInput(command, args, session));
    }

    public CmdDesc commandDescription(List<String> args) {
        // TODO
        return new CmdDesc(false);
    }


    private void clear(CommandInput input) {
        terminal().puts(InfoCmp.Capability.clear_screen);
        terminal().flush();
    }

    private void rubyExec(CommandInput input) {
        long start = System.currentTimeMillis();
        String command = parseCommand(input, false);
        execCommand(command, start);
    }

    private void execShellCommand(CommandInput input) {
        String command = parseCommand(input, true);
        execCommand(command, 0);
    }

    private void execCommand(String command, long start) {
        HBaseShellSession shellSession = loadShellSession();
        Result execute = shellSession.execute(command);
        if (execute.isSuccess()) {
            println(execute.getResult());
            if (start > 0) {
                println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
            }
        }else {
            println(execute.getResult());
            if (start > 0) {
                println("ERROR," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
            }
        }
    }

    @Override
    public String name() {
        return "hshell";
    }
}
