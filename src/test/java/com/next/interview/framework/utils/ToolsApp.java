package com.next.interview.framework.utils;

import com.next.interview.framework.domain.People;
import com.next.interview.framework.domain.User;
import org.junit.Test;

import java.lang.reflect.Field;

public class ToolsApp {
    @Test
    public void test02() throws Exception{
        Class<?> clazz = Class.forName("com.next.interview.framework.domain.User");
        Field field = clazz.getDeclaredField("name");
        System.out.println("field:" + field + "," + Tools.getColumn(field)+ ":" + Tools.getMethod(field));

        field = clazz.getDeclaredField("birthday");
        System.out.println(Tools.getColumn(field) + ":" + Tools.getMethod(field));

    }
    @Test
    public void test01() throws Exception{
        System.out.println(Tools.getTable(User.class));
        System.out.println(Tools.getTable(People.class));
    }
}
