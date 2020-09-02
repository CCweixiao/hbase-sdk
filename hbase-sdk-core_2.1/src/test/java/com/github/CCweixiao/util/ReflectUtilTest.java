package com.github.CCweixiao.util;

import com.github.CCweixiao.entity.UserEntity;
import com.github.CCweixiao.entity.UserEntity2;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * <p>反射工具测试类</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/29 4:10 下午
 * @since 1.0
 */
public class ReflectUtilTest {
    @Test
    public void testGetHBaseTableName() {
        String tableName = ReflectUtil.getHBaseTableName(UserEntity.class);
        String tableName2 = ReflectUtil.getHBaseTableName(UserEntity2.class);
        System.out.println(tableName);
        System.out.println(tableName2);
    }

    @Test
    public void testGetHBaseColumnName() {
        for (Field field : UserEntity2.class.getDeclaredFields()) {
            String colName = ReflectUtil.getHBaseColumnName("info1", field);
            System.out.println(colName);
        }
    }
}
