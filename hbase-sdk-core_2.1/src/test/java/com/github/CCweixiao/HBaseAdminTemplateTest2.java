package com.github.CCweixiao;

import org.junit.Test;

/**
 * @author leojie 2021/6/10 9:09 下午
 */
public class HBaseAdminTemplateTest2 {
    @Test
    public void testSingle(){

        HBaseAdminTemplate adminTemplate2 = new HBaseAdminTemplate("docker-hbase-remote", "2181");
//        HBaseAdminTemplate adminTemplate3 = new HBaseAdminTemplate("docker-hbase-remote", "2181");
//        System.out.println(adminTemplate2.getConnection() == adminTemplate3.getConnection());
        System.out.println(adminTemplate2.listNamespaceNames().size());
        System.out.println(adminTemplate2.listTableNames().size());

        HBaseAdminTemplate adminTemplate = new HBaseAdminTemplate("docker-hbase", "2181");
        System.out.println(adminTemplate.listNamespaceNames().size());
        System.out.println(adminTemplate.listTableNames().size());
        System.out.println("##################################################");


//        HBaseAdminTemplate adminTemplate3 = new HBaseAdminTemplate("docker-hbase", "2181");
//        System.out.println(adminTemplate3.listNamespaceNames());
    }
}
