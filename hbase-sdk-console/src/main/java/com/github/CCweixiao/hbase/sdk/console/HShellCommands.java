package com.github.CCweixiao.hbase.sdk.console;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseShellSessionEnvInitException;
import com.github.CCweixiao.hbase.sdk.shell.HBaseShellCommands;
import com.github.CCweixiao.hbase.sdk.shell.HBaseShellSession;
import com.github.CCweixiao.hbase.sdk.shell.HBaseShellSessionManager;
import com.github.CCweixiao.hbase.sdk.shell.Result;
import org.jline.builtins.Completers;
import org.jline.console.CmdDesc;
import org.jline.console.CommandInput;
import org.jline.console.CommandMethods;
import org.jline.console.CommandRegistry;
import org.jline.console.impl.JlineCommandRegistry;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.completer.SystemCompleter;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author leojie 2023/7/29 21:15
 */
public class HShellCommands extends JlineCommandRegistry implements CommandRegistry {
    private LineReader reader;
    private Exception exception;

    private final Map<String, CommandMethods> commandExecute = new HashMap<>();
    private final Map<String, List<String>> commandInfo = new HashMap<>();
    private final Map<String, String> aliasCommand = new HashMap<>();

    public HShellCommands() {
        try {
            Set<String> allCommands = HBaseShellCommands.getAllCommands();
            for (String commandName : allCommands) {
                commandExecute.put(commandName, new CommandMethods(this::execShellCommand, this::defaultCompleter));
            }
        } catch (IOException e) {
            throw new HBaseShellSessionEnvInitException(e);
        }

        commandExecute.put("clear", new CommandMethods(this::clear, this::defaultCompleter));
        commandInfo.put("clear", Collections.singletonList("clear all input."));
        registerCommands(commandExecute);
    }

    private HBaseShellSession loadShellSession() {
        Properties p = new Properties();
        p.setProperty("hbase.shell.session.debug.log", "true");
        p.setProperty("hbase.zookeeper.quorum", "myhbase");
        p.setProperty("hbase.zookeeper.property.clientPort", "2181");
        return HBaseShellSessionManager.getHBaseShellSession(p);
    }

    public void setLineReader(LineReader reader) {
        this.reader = reader;
    }

    private Terminal terminal() {
        return reader.getTerminal();
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
        exception = null;
        Object out = commandExecute.get(command(command)).execute().apply(new CommandInput(command, args, session));
        if (exception != null) {
            throw exception;
        }
        return out;
    }

    public CmdDesc commandDescription(List<String> args) {
        // TODO
        return new CmdDesc(false);
    }


    private void clear(CommandInput input) {
        try {
            terminal().puts(InfoCmp.Capability.clear_screen);
            terminal().flush();
        } catch (Exception e) {
            exception = e;
        }
    }

    private void execShellCommand(CommandInput input) {
        long start = System.currentTimeMillis();
        String command = parseCommand(input);
        HBaseShellSession shellSession = loadShellSession();
        Result execute = shellSession.execute(command);
        if (execute.isSuccess()) {
            terminal().writer().println(execute.getResult());
            terminal().writer().println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
        }else {
            terminal().writer().println(execute.getResult());
            terminal().writer().println("ERROR," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
        }
    }

    private String parseCommand(CommandInput input) {
        String[] args = input.args();
        if (args != null && args.length == 1) {
            String line = args[0];
            return line.substring(1, line.length() - 1);
        }
        StringBuilder sb = new StringBuilder();

        if (args != null && args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    sb.append(args[i].substring(1)).append(" ");
                } else if (i == args.length - 1) {
                    sb.append(args[i], 0, args[i].length() - 1).append(" ");
                } else {
                    sb.append(args[i]).append(" ");
                }
            }
        }
        return sb.toString();
    }

    private Set<String> capabilities() {
        return InfoCmp.getCapabilitiesByName().keySet();
    }

    private List<Completer> testCompleter(String command) {
        List<Completer> completerList = new ArrayList<>();
        completerList.add(new ArgumentCompleter(NullCompleter.INSTANCE,
                new Completers.OptionCompleter(new StringsCompleter(this::capabilities), this::commandOptions, 1)));
        return completerList;
    }
}
