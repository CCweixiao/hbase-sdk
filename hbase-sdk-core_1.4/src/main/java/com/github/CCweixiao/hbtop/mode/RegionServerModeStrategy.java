package com.github.CCweixiao.hbtop.mode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.ServerLoad;
import org.apache.hadoop.hbase.ServerName;
import org.apache.hadoop.hbase.classification.InterfaceAudience;
import com.github.CCweixiao.hbtop.Record;
import com.github.CCweixiao.hbtop.RecordFilter;
import com.github.CCweixiao.hbtop.field.Field;
import com.github.CCweixiao.hbtop.field.FieldInfo;
import com.github.CCweixiao.hbtop.field.Size;
import com.github.CCweixiao.hbtop.field.Size.Unit;

/**
 * @author leojie 2021/1/16 9:14 下午
 */
@InterfaceAudience.Private
public final class RegionServerModeStrategy implements ModeStrategy {

  private final List<FieldInfo> fieldInfos = Arrays.asList(
    new FieldInfo(Field.REGION_SERVER, 0, true),
    new FieldInfo(Field.LONG_REGION_SERVER, 0, false),
    new FieldInfo(Field.REGION_COUNT, 7, true),
    new FieldInfo(Field.REQUEST_COUNT_PER_SECOND, 10, true),
    new FieldInfo(Field.READ_REQUEST_COUNT_PER_SECOND, 10, true),
    new FieldInfo(Field.WRITE_REQUEST_COUNT_PER_SECOND, 10, true),
    new FieldInfo(Field.STORE_FILE_SIZE, 13, true),
    new FieldInfo(Field.UNCOMPRESSED_STORE_FILE_SIZE, 15, false),
    new FieldInfo(Field.NUM_STORE_FILES, 7, true),
    new FieldInfo(Field.MEM_STORE_SIZE, 11, true),
    new FieldInfo(Field.USED_HEAP_SIZE, 11, true),
    new FieldInfo(Field.MAX_HEAP_SIZE, 11, true)
  );

  private final RegionModeStrategy regionModeStrategy = new RegionModeStrategy();

  RegionServerModeStrategy(){
  }

  @Override
  public List<FieldInfo> getFieldInfos() {
    return fieldInfos;
  }

  @Override
  public Field getDefaultSortField() {
    return Field.REQUEST_COUNT_PER_SECOND;
  }

  @Override
  public List<Record> getRecords(ClusterStatus clusterStatus) {
    // Get records from RegionModeStrategy and add REGION_COUNT field
    List<Record> records = new ArrayList<>();
    for (Record record : regionModeStrategy.getRecords(clusterStatus)) {
      List<Record.Entry> entries = new ArrayList<>();
      for (FieldInfo fieldInfo : fieldInfos) {
        if (record.containsKey(fieldInfo.getField())) {
          entries.add(Record.entry(fieldInfo.getField(),
            record.get(fieldInfo.getField())));
        }
      }

      // Add REGION_COUNT field
      records.add(Record.builder().putAll(Record.ofEntries(entries))
        .put(Field.REGION_COUNT, 1).build());
    }

    // Aggregation by NAMESPACE field
    Map<String, Record> retMap = new HashMap<>();
    for (Record record : records) {
      String regionServer = record.get(Field.LONG_REGION_SERVER).asString();
      if (retMap.containsKey(regionServer)) {
        retMap.put(regionServer, retMap.get(regionServer).combine(record));
      } else {
        retMap.put(regionServer, record);
      }
    }

    // Add USED_HEAP_SIZE field and MAX_HEAP_SIZE field
    for (ServerName sn : clusterStatus.getServers()) {
      Record record = retMap.get(sn.getServerName());
      if (record == null) {
        continue;
      }
      ServerLoad sl = clusterStatus.getLoad(sn);
      Record newRecord = Record.builder().putAll(record)
        .put(Field.USED_HEAP_SIZE, new Size(sl.getUsedHeapMB(), Unit.MEGABYTE))
        .put(Field.MAX_HEAP_SIZE, new Size(sl.getMaxHeapMB(), Unit.MEGABYTE)).build();
      retMap.put(sn.getServerName(), newRecord);
    }
    return new ArrayList<>(retMap.values());
  }

  @Override
  public DrillDownInfo drillDown(Record selectedRecord) {
    List<RecordFilter> initialFilters = Collections.singletonList(RecordFilter
      .newBuilder(Field.REGION_SERVER)
      .doubleEquals(selectedRecord.get(Field.REGION_SERVER)));
    return new DrillDownInfo(Mode.REGION, initialFilters);
  }
}
