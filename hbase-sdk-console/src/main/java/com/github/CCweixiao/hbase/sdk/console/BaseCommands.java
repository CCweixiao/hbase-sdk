package com.github.CCweixiao.hbase.sdk.console;

import org.jline.console.Printer;
import org.jline.console.impl.JlineCommandRegistry;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2023/7/30 19:10
 */
public abstract class BaseCommands extends JlineCommandRegistry {
    private LineReader reader;
    private final Printer printer;
    public BaseCommands(Printer printer) {
        this.printer = printer;
    }

    protected void println(Object message) {
        String option = System.getProperty("hql.dev");
        if ("true".equalsIgnoreCase(option)) {
            this.terminal().writer().println(message);
        } else {
            Map<String, Object> options = new HashMap<>();
            options.put(Printer.STRUCT_ON_TABLE, true);
            options.put(Printer.VALUE_STYLE, "classpath:/hbase/sdk/console/gron.nanorc");
            printer.println(options, message);
        }
    }

    protected Terminal terminal() {
        return reader.getTerminal();
    }

    public LineReader getReader() {
        return reader;
    }

    public void setReader(LineReader reader) {
        this.reader = reader;
    }
}
