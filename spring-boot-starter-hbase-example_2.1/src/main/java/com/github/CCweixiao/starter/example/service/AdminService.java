package com.github.CCweixiao.starter.example.service;

import com.github.CCweixiao.HBaseAdminTemplate;
import com.github.CCweixiao.model.SnapshotDesc;
import org.apache.hadoop.hbase.client.SnapshotDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leojie 2020/9/2 10:07 下午
 */
@Service
public class AdminService {
    @Autowired
    private HBaseAdminTemplate hBaseAdminTemplate;

    public List<SnapshotDesc> getAllSnapshot() {
        return hBaseAdminTemplate.listSnapshots();
    }
}
