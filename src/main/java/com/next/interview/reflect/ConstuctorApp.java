package com.next.interview.reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;

/*
    如何使用反射来调用构造器
    1) 如何获取构造器 getConstuctor/getConstuctors
    2) 如何使用指定的构造器实例化对象 newInstance
 */
public class ConstuctorApp {
    @Test
    public void test02() throws Exception{
        Class<?> clazz = Class.forName("com.next.interview.reflect.Student");
        Constructor constructor = clazz.getConstructor(String.class);

        Object obj = constructor.newInstance("pk哥");
        System.out.println(obj);


    }
    @Test
    public void test01() throws ClassNotFoundException{
        Class<?> clazz = Class.forName("com.next.interview.reflect.Student");

        Constructor<?>[] constructors = clazz.getConstructors();

        for(Constructor constructor: constructors){
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            System.out.println(constructor.getName());

            for(Class<?> parameterType : parameterTypes){
                System.out.println("..." + parameterType);
            }
        }
    }
}
