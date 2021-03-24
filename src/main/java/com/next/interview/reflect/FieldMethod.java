package com.next.interview.reflect;

import org.junit.Test;

import java.lang.reflect.Field;

/*
    类里面的私有属性可以设置值
 */
public class FieldMethod {
    @Test
    public void test02() throws Exception {
        Class<?> clazz = Class.forName("com.next.interview.reflect.Student");
        Student student = (Student)clazz.newInstance();
        Field field = clazz.getDeclaredField("sex");

        //如果不设置可访问为True，则java.lang.IllegalAccessException: Class com.next.interview.reflect.FieldMethod can not access a member of class com.next.interview.reflect.Student with modifiers "private"
        if (!field.isAccessible()){
            field.setAccessible(true);
        }
        field.set(student, "男");

        System.out.println(student);

    }
    @Test
    public void test01() throws Exception{
        Class<?> clazz = Class.forName("com.next.interview.reflect.Student");

        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field field: declaredFields){
            System.out.println(field.getType() + " : " + field.getName());
        }

        System.out.println("~~~~~~~");

        Field field = clazz.getDeclaredField("sex");
        System.out.println(field);
    }
}
