package com.leo.hbase.sdk.core;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * HBase admin template test
 *
 * @author leojie 2020/8/15 10:28 上午
 */
public class HBaseAdminTemplateTest {
    private HBaseAdminTemplate hBaseTemplate;

    @Before
    public void initHBaseTemplate() {
        hBaseTemplate = new HBaseAdminTemplate("node1", "2181");
    }

    @Test
    public void testListNamespace() {
        List<String> namespaces = hBaseTemplate.listNamespaces();

        System.out.println(namespaces);
    }

    @Test
    public void testCreateNamespace() {

    }


}
