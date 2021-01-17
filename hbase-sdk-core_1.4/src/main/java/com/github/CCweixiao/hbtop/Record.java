package com.github.CCweixiao.hbtop;

import com.google.common.collect.ImmutableMap;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.hadoop.hbase.classification.InterfaceAudience;
import com.github.CCweixiao.hbtop.field.Field;
import com.github.CCweixiao.hbtop.field.FieldValue;
import com.github.CCweixiao.hbtop.field.FieldValueType;


/**
 * @author leojie 2021/1/16 9:14 下午
 */
@InterfaceAudience.Private
public final class Record implements Map<Field, FieldValue> {

  private final ImmutableMap<Field, FieldValue> values;

  public final static class Entry extends AbstractMap.SimpleImmutableEntry<Field, FieldValue> {
    private Entry(Field key, FieldValue value) {
      super(key, value);
    }
  }

  public final static class Builder {

    private final ImmutableMap.Builder<Field, FieldValue> builder;

    private Builder() {
      builder = ImmutableMap.builder();
    }

    public Builder put(Field key, Object value) {
      builder.put(key, key.newValue(value));
      return this;
    }

    public Builder put(Field key, FieldValue value) {
      builder.put(key, value);
      return this;
    }

    public Builder put(Entry entry) {
      builder.put(entry);
      return this;
    }

    public Builder putAll(Map<Field, FieldValue> map) {
      builder.putAll(map);
      return this;
    }

    public Record build() {
      return new Record(builder.build());
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Entry entry(Field field, Object value) {
    return new Entry(field, field.newValue(value));
  }

  public static Entry entry(Field field, FieldValue value) {
    return new Entry(field, value);
  }

  public static Record ofEntries(Entry... entries) {
    Builder builder = builder();
    for (Entry entry : entries) {
      builder.put(entry.getKey(), entry.getValue());
    }
    return builder.build();
  }

  public static Record ofEntries(Iterable<Entry> entries) {
    Builder builder = builder();
    for (Entry entry : entries) {
      builder.put(entry.getKey(), entry.getValue());
    }
    return builder.build();
  }

  private Record(ImmutableMap<Field, FieldValue> values) {
    this.values = values;
  }

  @Override
  public int size() {
    return values.size();
  }

  @Override
  public boolean isEmpty() {
    return values.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return values.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return values.containsValue(value);
  }

  @Override
  public FieldValue get(Object key) {
    return values.get(key);
  }

  @Override
  public FieldValue put(Field key, FieldValue value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public FieldValue remove(Object key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void putAll(@NonNull Map<? extends Field, ? extends FieldValue> m) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  @NonNull
  public Set<Field> keySet() {
    return values.keySet();
  }

  @Override
  @NonNull
  public Collection<FieldValue> values() {
    return values.values();
  }

  @Override
  @NonNull
  public Set<Map.Entry<Field, FieldValue>> entrySet() {
    return values.entrySet();
  }

  public Record combine(Record o) {
    Builder builder = builder();
    for (Field k : values.keySet()) {
      if (k.getFieldValueType() == FieldValueType.STRING) {
        builder.put(k, values.get(k));
      } else {
        builder.put(k, values.get(k).plus(o.values.get(k)));
      }
    }
    return builder.build();
  }
}