package com.github.CCweixiao.hbase.sdk.console;

import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.template.HBaseSqlTemplate;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import org.jline.console.CmdDesc;
import org.jline.console.CommandInput;
import org.jline.console.CommandMethods;
import org.jline.console.Printer;
import org.jline.reader.impl.completer.SystemCompleter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author leojie 2023/7/29 21:15
 */
public class HqlCommands extends BaseCommands {

    private final Map<String, CommandMethods> commandExecute = new HashMap<>();
    private final Map<String, List<String>> commandInfo = new HashMap<>();
    private final Map<String, String> aliasCommand = new HashMap<>();

    public HqlCommands(Printer printer) {
        super(printer);
        commandExecute.put("select", new CommandMethods(this::select, this::defaultCompleter));
        commandExecute.put("insert", new CommandMethods(this::insert, this::defaultCompleter));
        commandExecute.put("delete", new CommandMethods(this::delete, this::defaultCompleter));
        registerCommands(commandExecute);
    }

    private HBaseTableSchema defaultHBaseTableSchema() {
        return HBaseTableSchema.of("test:test_sql")
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

    private void select(CommandInput input) {
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        HBaseSqlTemplate sqlTemplate = HBaseSqlTemplate.of(HClusterContext.getInstance().getCurrentClusterProperties());
        HBaseDataSet dataSet = sqlTemplate.select(hql);
        String table = dataSet.showTable(true);
        println(table);
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void insert(CommandInput input) {
        HBaseSqlTemplate sqlTemplate = HBaseSqlTemplate.of(HClusterContext.getInstance().getCurrentClusterProperties());
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        sqlTemplate.insert(hql);
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void delete(CommandInput input) {
        HBaseSqlTemplate sqlTemplate = HBaseSqlTemplate.of(HClusterContext.getInstance().getCurrentClusterProperties());
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        sqlTemplate.delete(hql);
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private String parseSql(CommandInput input) {
        return this.parseCommand(input, true);
    }

    @Override
    public String name() {
        return "hql";
    }
}
