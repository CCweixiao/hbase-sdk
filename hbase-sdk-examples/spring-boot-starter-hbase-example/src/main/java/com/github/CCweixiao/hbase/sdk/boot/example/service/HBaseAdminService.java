package com.github.CCweixiao.hbase.sdk.boot.example.service;

import com.github.CCweixiao.hbase.sdk.template.IHBaseAdminTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leojie
 */
@Service
public class HBaseAdminService {
    @Autowired
    private IHBaseAdminTemplate hBaseTemplate;


    public List<String> allTables() {
        return  hBaseTemplate.listTableNames();
    }

}
