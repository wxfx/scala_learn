package com.next.interview.reflect;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/*
    BeanUtils的使用
 */

public class BeanUtilsApp {
    @Test
    public void test02() throws Exception{
       Map<String, Object> map = new HashMap<String, Object>();
       map.put("name", "广州塔");
       map.put("age", 18);

        Student student = new Student();
        //populate：输入数据
        BeanUtils.populate(student, map);
        System.out.println(student);
    }
    @Test
    public void test01() throws Exception{
        Student student = new Student();
        BeanUtils.setProperty(student, "name", "yan");
        System.out.println(student);
    }
}
