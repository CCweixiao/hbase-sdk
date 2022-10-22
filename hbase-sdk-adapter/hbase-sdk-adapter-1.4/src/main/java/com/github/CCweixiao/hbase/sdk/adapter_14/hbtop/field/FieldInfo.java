/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.CCweixiao.hbase.sdk.adapter_14.hbtop.field;

import java.util.Objects;

import org.apache.hadoop.hbase.classification.InterfaceAudience;

/**
 * @author leojie 2021/1/16 9:14 下午
 */
@InterfaceAudience.Private
public class FieldInfo {
  private final Field field;
  private final int defaultLength;
  private final boolean displayByDefault;

  public FieldInfo(Field field, int defaultLength, boolean displayByDefault) {
    this.field = Objects.requireNonNull(field);
    this.defaultLength = defaultLength;
    this.displayByDefault = displayByDefault;
  }

  public Field getField() {
    return field;
  }

  public int getDefaultLength() {
    return defaultLength;
  }

  public boolean isDisplayByDefault() {
    return displayByDefault;
  }
}
