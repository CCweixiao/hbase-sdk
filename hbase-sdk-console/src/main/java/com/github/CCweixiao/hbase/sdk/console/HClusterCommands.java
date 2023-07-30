package com.github.CCweixiao.hbase.sdk.console;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseShellSessionEnvInitException;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.template.HBaseAdminTemplate;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import org.apache.commons.io.IOUtils;
import org.jline.console.CmdDesc;
import org.jline.console.CommandInput;
import org.jline.console.CommandMethods;
import org.jline.console.CommandRegistry;
import org.jline.console.impl.JlineCommandRegistry;
import org.jline.reader.LineReader;
import org.jline.reader.impl.completer.SystemCompleter;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author leojie 2023/7/29 21:15
 */
public class HClusterCommands extends JlineCommandRegistry implements CommandRegistry {
    private LineReader reader;
    private Exception exception;

    private final Map<String, CommandMethods> commandExecute = new HashMap<>();
    private final Map<String, List<String>> commandInfo = new HashMap<>();
    private final Map<String, String> aliasCommand = new HashMap<>();

    public HClusterCommands() {
        commandExecute.put("list_clusters", new CommandMethods(this::listClusters, this::defaultCompleter));
        commandExecute.put("add_cluster", new CommandMethods(this::addCluster, this::defaultCompleter));
        commandExecute.put("remove_cluster", new CommandMethods(this::removeCluster, this::defaultCompleter));
        commandExecute.put("switch_cluster", new CommandMethods(this::switchCluster, this::defaultCompleter));
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
       // adminTemplate = HBaseAdminTemplate.of(p);
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


    private void listClusters(CommandInput input) {
        long start = System.currentTimeMillis();
        File clusterConfDirFile = HClusterContext.getInstance().getClusterConfDirFile();
        String[] confFiles = clusterConfDirFile.list();
        if (confFiles.length < 1) {
            terminal().writer().println("The cluster has not been added, please use the add_cluster command.");
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < confFiles.length; i++) {
                result.append(i + ": ");
                result.append(confFiles[i], 0, confFiles[i].lastIndexOf(".properties"));
                result.append("\n");
            }
            terminal().writer().println("Existing cluster list: \n");
            terminal().writer().println(result);
        }

        terminal().writer().println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void addCluster(CommandInput input) {
        long start = System.currentTimeMillis();
        String command = parseCommand(input);
        String[] commands = command.split("\\s+");
        if (commands.length != 3) {
            terminal().writer().println("Failed to add the cluster, please enter the correct command, such as:\n");
            terminal().writer().println("add_cluster localhost hbase.zookeeper.quorum=localhost;" +
                    "hbase.zookeeper.property.clientPort=2181");
            return;
        }
        String clusterName = commands[1];
        String configText = commands[2];
        if (StringUtil.isBlank(configText)) {
            terminal().writer().println("Failed to add the cluster, please enter the correct command, such as:\n");
            terminal().writer().println("add_cluster localhost hbase.zookeeper.quorum=localhost;" +
                    "hbase.zookeeper.property.clientPort=2181");
            return;
        }
        String[] configs = configText.trim().split(";");
        boolean res = testClusterInfo(configs);
        if (!res) {
            return;
        }
        terminal().writer().println("The newly added cluster connection test is successful.");
        String clusterConfDirPath = HClusterContext.getInstance().getClusterConfigDirPath();
        File clusterConfFile = new File(clusterConfDirPath.concat(File.separator).concat(clusterName)
                .concat(".properties"));
        if (clusterConfFile.exists()) {
            terminal().writer().println(String.format("The configuration for cluster %s already exists.", clusterName));
            return;
        }
        try {
            if (clusterConfFile.createNewFile()) {
                FileOutputStream out = new FileOutputStream(clusterConfFile);
                for (String config : configs) {
                    IOUtils.write(config + "\n", out);
                }
                IOUtils.closeQuietly(out);
            } else {
                terminal().writer().println(String.format("Failed to create cluster configuration file %s .",
                        clusterConfFile.getAbsolutePath()));
            }
        } catch (IOException e) {
            terminal().writer().println(String.format("Failed to create cluster configuration file %s .",
                    clusterConfFile.getAbsolutePath()));
            throw new HBaseShellSessionEnvInitException(e);
        }

        terminal().writer().println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private boolean testClusterInfo(String[] configs) {
        if (configs == null || configs.length == 0) {
            terminal().writer().println("Please specify the cluster connection configuration, k1=v1;k2=v2 format.");
            return false;
        }
        String formatError = "Make sure that a single configuration item is in k=v format.";
        Properties p = new Properties();
        for (String config : configs) {
            if (StringUtil.isBlank(config)) {
                terminal().writer().println(formatError);
                return false;
            }
            String[] kv = config.split("=");
            if (kv.length != 2) {
                terminal().writer().println(formatError);
                return false;
            }
            p.setProperty(kv[0], kv[1]);
        }

        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            HBaseAdminTemplate adminTemplate = HBaseAdminTemplate.of(p);
            List<String> namespaces = adminTemplate.listNamespaceNames();
            return namespaces != null && !namespaces.isEmpty();
        });
        boolean res = false;
        // 获取执行结果
        try {
            res = future.get(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            terminal().writer().println("abnormal");
            future.cancel(true);
            throw new HBaseShellSessionEnvInitException(e);
        } catch (ExecutionException e) {
            terminal().writer().println("An exception occurred during the cluster connection test.");
            future.cancel(true);
            throw new HBaseShellSessionEnvInitException(e);
        } catch (TimeoutException e) {
            // 超时了，结束该方法的执行
            terminal().writer().println("The cluster connection test timed out, please check the configuration or cluster status.");
            future.cancel(true);
        }
        return res;
    }

    private void removeCluster(CommandInput input) {
        long start = System.currentTimeMillis();
        String command = parseCommand(input);
        String[] commands = command.split("\\s+");
        if (commands.length != 2) {
            terminal().writer().println("Failed to remove cluster, please enter the correct command, such as:\n");
            terminal().writer().println("remove_cluster localhost");
            return;
        }
        String clusterName = commands[1];
        String clusterConfDirPath = HClusterContext.getInstance().getClusterConfigDirPath();
        File clusterConfFile = new File(clusterConfDirPath.concat(File.separator).concat(clusterName)
                .concat(".properties"));
        if (!clusterConfFile.exists()) {
            terminal().writer().println(String.format("Cluster %s does not exist.", clusterName));
            return;
        }
        boolean delete = clusterConfFile.delete();
        if (delete) {
            terminal().writer().println(String.format("Cluster %s is successfully deleted.", clusterName));
        } else {
            terminal().writer().println(String.format("Cluster %s failed to be deleted.", clusterName));

        }
        terminal().writer().println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void switchCluster(CommandInput input) {
        String command = parseCommand(input);
        String[] commands = command.split("\\s+");
        if (commands.length != 2) {
            terminal().writer().println("Failed to switch cluster, please enter the correct command, such as:\n");
            terminal().writer().println("switch_cluster localhost");
            return;
        }
        HClusterContext.getInstance().setCurrentSelectedCluster(commands[1].trim());
        terminal().writer().println(String.format("The currently selected cluster is %s.", commands[1]));
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

    @Override
    public String name() {
        return "hql";
    }
}
