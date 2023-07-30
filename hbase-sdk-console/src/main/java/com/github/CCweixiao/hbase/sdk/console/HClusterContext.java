package com.github.CCweixiao.hbase.sdk.console;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseShellSessionEnvInitException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2023/7/30 10:48
 */
public class HClusterContext {
    private volatile static Map<String, Properties> clusterConf;
    private String currentSelectedCluster;

    public void setCurrentSelectedCluster(String clusterName) {
        currentSelectedCluster = clusterName;
    }

    public String getCurrentSelectedCluster() {
        return currentSelectedCluster;
    }

    private HClusterContext() {

    }
    public static class HClusterContextHolder {
        public static final HClusterContext INSTANCE = new HClusterContext();
    }

    public static HClusterContext getInstance() {
        return HClusterContextHolder.INSTANCE;
    }


    public Properties getCurrentClusterProperties() {
        if (StringUtil.isBlank(getCurrentSelectedCluster())) {
            throw new HBaseShellSessionEnvInitException("Please switch cluster first.");
        }
        return getClusterProperties(getCurrentSelectedCluster());
    }

    private Properties getClusterProperties(String clusterName) {
        if (clusterConf == null || !clusterConf.containsKey(clusterName)) {
            synchronized (HClusterContext.class) {
                if (clusterConf == null || !clusterConf.containsKey(clusterName)) {
                    if (clusterConf == null) {
                        clusterConf = new HashMap<>(2);
                    }
                    if (!clusterConf.containsKey(clusterName)) {
                        Properties p = readConf(clusterName);
                        clusterConf.put(clusterName, p);
                        return readConf(clusterName);
                    }
                }
            }
        }
        return clusterConf.get(clusterName);
    }

    private Properties readConf(String clusterName) {
        Properties p = new Properties();
        String clusterConfDirPath = getClusterConfigDirPath();
        File clusterConfFile = new File(clusterConfDirPath.concat(File.separator).concat(clusterName)
                .concat(".properties"));
        if (!clusterConfFile.exists()) {
            throw new HBaseShellSessionEnvInitException(String.format("The cluster %s not exists.", clusterName));
        }
        try (FileInputStream out = new FileInputStream(clusterConfFile)) {
            List<String> lines = IOUtils.readLines(out);
            for (String line : lines) {
                String[] kv = line.split("=");
                p.setProperty(kv[0], kv[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    public File getClusterConfDirFile() {
        String clusterConfDirPath = getClusterConfigDirPath();
        File clusterConfDirFile = new File(clusterConfDirPath);
        if (!clusterConfDirFile.exists()) {
            clusterConfDirFile.mkdir();
        }
        if (!clusterConfDirFile.isDirectory()) {
            throw new HBaseShellSessionEnvInitException("Please ensure that the default storage path of the cluster" +
                    " configuration file is a folder.");
        }
        return clusterConfDirFile;
    }

    public String getClusterConfigDirPath() {
        File userDirFile = new File(System.getProperty("user.dir"));
        return userDirFile.getAbsolutePath().concat(File.separator).concat("cluster_info_conf");
    }
}
