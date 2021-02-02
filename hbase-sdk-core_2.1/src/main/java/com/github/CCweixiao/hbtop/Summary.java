package com.github.CCweixiao.hbtop;


import org.apache.yetus.audience.InterfaceAudience;


import java.util.Objects;

/**
 * @author leojie 2021/1/16 9:56 下午
 */
@InterfaceAudience.Private
public class Summary {
    private final String currentTime;
    private final String version;
    private final String clusterId;
    private final int servers;
    private final int liveServers;
    private final int deadServers;
    private final int namespaceCount;
    private final int tableCount;
    private final int snapshotCount;

    private final int regionCount;
    private final int ritCount;
    private final double averageLoad;
    private final long aggregateRequestPerSecond;

    public Summary(String currentTime, String version, String clusterId, int servers,
                   int liveServers, int deadServers,int namespaceCount, int tableCount, int snapshotCount,
                   int regionCount, int ritCount, double averageLoad,
                   long aggregateRequestPerSecond) {
        this.currentTime = Objects.requireNonNull(currentTime);
        this.version = Objects.requireNonNull(version);
        this.clusterId = Objects.requireNonNull(clusterId);
        this.servers = servers;
        this.liveServers = liveServers;
        this.deadServers = deadServers;
        this.namespaceCount = namespaceCount;
        this.tableCount = tableCount;
        this.snapshotCount = snapshotCount;
        this.regionCount = regionCount;
        this.ritCount = ritCount;
        this.averageLoad = averageLoad;
        this.aggregateRequestPerSecond = aggregateRequestPerSecond;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getVersion() {
        return version;
    }

    public String getClusterId() {
        return clusterId;
    }

    public int getServers() {
        return servers;
    }

    public int getLiveServers() {
        return liveServers;
    }

    public int getDeadServers() {
        return deadServers;
    }

    public int getNamespaceCount() {
        return namespaceCount;
    }

    public int getTableCount() {
        return tableCount;
    }

    public int getSnapshotCount() {
        return snapshotCount;
    }

    public int getRegionCount() {
        return regionCount;
    }

    public int getRitCount() {
        return ritCount;
    }

    public double getAverageLoad() {
        return averageLoad;
    }

    public long getAggregateRequestPerSecond() {
        return aggregateRequestPerSecond;
    }
}
