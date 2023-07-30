package com.github.CCweixiao.hbase.sdk.console;

import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.shell.HBaseShellCommands;
import org.jline.builtins.ConfigurationPath;
import org.jline.console.impl.Builtins;
import org.jline.console.impl.SystemRegistryImpl;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.MaskingCallback;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.DefaultParser.Bracket;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.OSUtils;
import org.jline.widget.AutopairWidgets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author leojie 2023/7/29 20:18
 */
public class HqlConsoleApp {

    private static final String WELCOME_MESSAGE = "                       -Q\n" +
            "                      .QQ\n" +
            "                      SQQ)\n" +
            "                      QQQN\n" +
            "                      RQQQ!\n" +
            "                      TQQQQQQQQQQQQQQQQT,\n" +
            "                      4QQQQQQQQQQQQQQQQQQQQ\n" +
            "                      6QQQQQQQQQQQQQQQQQQQQQQ-\n" +
            "                     ;QQQQQQQQQQQQQQQQQQQQQQQQE\n" +
            "                    :QQQQQQQQQQQQQQQQQY     CQQQ\n" +
            "                   1QQQQQQQQQQQQQQQQ5    GQQQM(QQ\n" +
            "               ,! 6QQQQQQQQQQQQQQQQ6  (QO  .UMQQQQ-\n" +
            "               QQ\"QQQQQQQQQQQQQQQQR 1Q         JQQQ\"\n" +
            "              UQXQQQQQQQQQQQQQQQQQ M.           MR )ME\n" +
            "              KMQQQQQQQQQQQQQQB666\"...         E !!.  Q!\n" +
            "              ;WQQQQQQQQQQQQ0QQQQQQQQQ.       E  4H--- Q\n" +
            "               QQQQQQQ, DQQFQQQQQQQQQ9        HU\"     5Q\n" +
            "              NQQQQQM   QQ!QQQQQQQQQ.         7        \"\n" +
            "             .QQQQQQ  &QQRQQQQQQQ3\n" +
            "             &QQQQQQQQQ: VQ.          \n" +
            "             SQQQQQQQQ(  M             \n" +
            "             TQQQQQQB )E.             \n" +
            "             (QQQQ4 R\"              \n" +
            "              QQQ.E                           \n" +
            "               YI                                    \n" +
            "          __  ______    __                                  \n" +
            "        / / / / __ \\  / /                                   \n" +
            "       / /_/ / / / / / /                                    \n" +
            "      / __  / /_/ / / /___    v3.0.1                        \n" +
            "     /_/ /_/\\___\\_\\/_____/    Copyright © 2020 - 2023 leojie.    ";

    public static void main(String[] args) {

        try {
            Terminal terminal = TerminalBuilder.builder().system(true).build();
            DefaultParser parser = new DefaultParser();
            parser.setEofOnUnclosedBracket(Bracket.CURLY, Bracket.ROUND, Bracket.SQUARE);
            parser.setEofOnUnclosedQuote(true);
            parser.setRegexCommand("[:]{0,1}[a-zA-Z!]{1,}\\S*"); // change default regex to support shell commands
            parser.blockCommentDelims(new DefaultParser.BlockCommentDelims("/*", "*/"))
                    .lineCommentDelims(new String[]{"//"});

            Supplier<Path> workDir = () -> Paths.get(System.getProperty("user.dir"));
            ConfigurationPath configPath = new ConfigurationPath(Paths.get("."), Paths.get("."));
            Builtins builtins = new Builtins(workDir, configPath, null);
            HqlCommands hqlCommands = new HqlCommands();
            HShellCommands shellCommands = new HShellCommands();
            HClusterCommands clusterCommands = new HClusterCommands();

            SystemRegistryImpl masterRegistry = new SystemRegistryImpl(parser, terminal, workDir, configPath);
            masterRegistry.setCommandRegistries(hqlCommands, shellCommands, clusterCommands, builtins);
            Set<String> allShellCommands = HBaseShellCommands.getAllCommands();
            masterRegistry.addCompleter(new StringsCompleter(allShellCommands));

            LineReader lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(masterRegistry.completer())
                    .parser(parser)
                    .variable(LineReader.SECONDARY_PROMPT_PATTERN, "%M%P > ")
                    .variable(LineReader.INDENTATION, 2)
                    .variable(LineReader.LIST_MAX, 500)
                    .variable(LineReader.HISTORY_FILE, getHistoryCommandsFile())
                    .option(LineReader.Option.INSERT_BRACKET, true)
                    .option(LineReader.Option.EMPTY_WORD_OPTIONS, false)
                    .option(LineReader.Option.USE_FORWARD_SLASH, true)
                    .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true)
                    .build();
            AutopairWidgets autopairWidgets = new AutopairWidgets(lineReader);
            autopairWidgets.enable();
            if (OSUtils.IS_WINDOWS) {
                lineReader.setVariable(LineReader.BLINK_MATCHING_PAREN, 0);
            }
            builtins.setLineReader(lineReader);
            hqlCommands.setLineReader(lineReader);
            shellCommands.setLineReader(lineReader);
            clusterCommands.setLineReader(lineReader);
            terminal.writer().append(WELCOME_MESSAGE);
            terminal.writer().append("\n");
            while (true) {
                try {
                    masterRegistry.cleanUp();
                    String line = lineReader.readLine("hql> ", null, (MaskingCallback) null, null);
                    if (StringUtil.isBlank(line)) {
                        masterRegistry.execute(line);
                    } else {
                        String command = parser.getCommand(line);
                        command = command + " `" + line + "`";
                        masterRegistry.execute(command);
                    }
                } catch (UserInterruptException e) {
                    // Ignore
                } catch (EndOfFileException e) {
                    break;
                } catch (Exception | Error e) {
                    masterRegistry.trace(true, e);
                }
            }
            masterRegistry.close();

        } catch (IOException e) {
            throw new RuntimeException("Create terminal failed.");
        }
    }

    public static String help() {
        return "help >>>";
    }

    private static File getHistoryCommandsFile() throws IOException {
        return getHistoryCommandsFile("");
    }

    private static File getHistoryCommandsFile(String path) throws IOException {
        if (StringUtil.isBlank(path)) {
            path = System.getProperty("java.io.tmpdir");
        }
        if (StringUtil.isBlank(path)) {
            throw new IOException("Please specify the file path to save the history commands.");
        }
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            throw new FileNotFoundException(String.format("The directory path %s " +
                    "for saving historical commands does not exist.", path));
        }
        if (!dirFile.isDirectory()) {
            throw new FileNotFoundException(String.format("The path %s is not a directory.", path));
        }
        String fileName = "hql_history.log";
        String filePath = path.concat(File.separator).concat(fileName);
        File file = new File(filePath);
        if (!file.exists()) {
            boolean res = file.createNewFile();
            if (!res) {
                throw new IOException(String.format("Failed to create file %s " +
                        "for saving historical commands.", filePath));
            }

        }
        return file;
    }
}
