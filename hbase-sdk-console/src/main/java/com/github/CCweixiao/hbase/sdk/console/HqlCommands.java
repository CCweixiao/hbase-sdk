package com.github.CCweixiao.hbase.sdk.console;

import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.template.BaseHBaseSqlTemplate;
import com.github.CCweixiao.hbase.sdk.template.HBaseSqlTemplate;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
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
public class HqlCommands extends JlineCommandRegistry implements CommandRegistry {
    private LineReader reader;
    private Exception exception;
    private BaseHBaseSqlTemplate sqlTemplate;

    private final Map<String, CommandMethods> commandExecute = new HashMap<>();
    private final Map<String, List<String>> commandInfo = new HashMap<>();
    private final Map<String, String> aliasCommand = new HashMap<>();

    public HqlCommands() {
        commandExecute.put("select", new CommandMethods(this::select, this::defaultCompleter));
        commandExecute.put("insert", new CommandMethods(this::insert, this::defaultCompleter));
        commandExecute.put("delete", new CommandMethods(this::delete, this::defaultCompleter));
        commandExecute.put("clear", new CommandMethods(this::clear, this::defaultCompleter));
        commandInfo.put("clear", Collections.singletonList("clear all input."));
        registerCommands(commandExecute);
        this.init();
    }

    private void init() {
        Properties p = new Properties();
        p.setProperty("hbase.shell.session.debug.log", "true");
        p.setProperty("hbase.zookeeper.quorum", "myhbase");
        p.setProperty("hbase.zookeeper.property.clientPort", "2181");
        HBaseTableSchema tableSchema = HBaseTableSchema.of("test:test_sql")
                .addColumn("f1", "id")
                .addColumn("f1", "name")
                .addColumn("f1", "age", ColumnType.IntegerType)
                .addColumn("f1", "job")
                .addColumn("f1", "pay", ColumnType.DoubleType)
                .addColumn("f2", "address")
                .addColumn("f2", "commuter")
                .addRow("row_key")
                .scanBatch(100)
                .scanCaching(1000)
                .deleteBatch(100)
                .scanCacheBlocks(false)
                .build();
        sqlTemplate = HBaseSqlTemplate.of(p);
        sqlTemplate.registerTableSchema(tableSchema);
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

    private void select(CommandInput input) {
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        HBaseDataSet dataSet = sqlTemplate.select(hql);
        String table = dataSet.showTable(true);
        terminal().writer().println(table);
        terminal().writer().println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void insert(CommandInput input) {
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        sqlTemplate.insert(hql);
        terminal().writer().println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void delete(CommandInput input) {
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        sqlTemplate.delete(hql);
        terminal().writer().println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private String parseSql(CommandInput input) {
        StringBuilder sb = new StringBuilder();

        String[] args = input.args();
        if (args != null && args.length > 0) {
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
