package com.github.CCweixiao.hbase.sdk.common.reflect;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.github.CCweixiao.hbase.sdk.common.reflect.model.CityModel;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author leojie 2022/11/20 11:23
 */
public class ReflectAsmFactoryTest {
    @Test
    public void testGetMethods() {
        String[] methodNames = MethodAccess.get(CityModel.class).getMethodNames();
        System.out.println(methodNames);
    }

    @Test
    public void testGetFields() {
        Field[] fieldNames = FieldAccess.get(CityModel.class).getFields();
        System.out.println(fieldNames);
    }
}
