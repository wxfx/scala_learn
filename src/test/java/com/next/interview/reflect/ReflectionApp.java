package com.next.interview.reflect;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/*
    如何获取Class对象
    1)Class.forName()
    2)class.class/object.getClass()
 */
public class ReflectionApp {
    @Test
    public void test05() throws Exception{
        List<String> teachers = new ArrayList<String>();
        teachers.add("仓老师");
        teachers.add("龙老师");
        //类型不匹配
        //teachers.add(1000);

        Class<?> clazz = teachers.getClass();
        System.out.println(clazz);

        Method method = clazz.getDeclaredMethod("add", Object.class);
        System.out.println(method);
        //通过反射调用方法，通过反射能够跨过泛型限制
        method.invoke(teachers, 10000);
        method.invoke(teachers, "波老师");
        System.out.println(teachers);
        for(Object obj: teachers){
            System.out.println(obj);
        }



    }

    class Parent{}
    class Child extends Parent{}

    @Test
    public void test04() throws Exception{
        //class ReflectionApp$Child
        Class<?> classType = Child.class;
        System.out.println(classType);

        System.out.println("~~~~~~~~~~~");
        //获取父类
        //class ReflectionApp$Parent
        classType = classType.getSuperclass();
        System.out.println(classType);

        System.out.println("~~~~~~~~~~~");
        //class java.lang.Object
        classType = classType.getSuperclass();
        System.out.println(classType);

        System.out.println("~~~~~~~~~~~");
        //null
        classType = classType.getSuperclass();
        System.out.println(classType);

        System.out.println("~~~~~~~~~~~");
    }

    @Test
    public void test03() throws Exception{
        System.out.println(Integer.class);
        System.out.println(Integer.TYPE);
    }
    @Test
    public void test02() throws Exception{
        String name = "反射一定要学好";

        Class<?> clazz = name.getClass();
        System.out.println(clazz);

        System.out.println("~~~~~~~~~~~");

        clazz = String.class;
        System.out.println(clazz);


    }
    @Test
    public void test01() throws Exception{
        Class<?> clazz = Class.forName("java.lang.Object");
        System.out.println(clazz);

        System.out.println("~~~~~~~~~~~");
        //获取所有方法（共有的、私有的和保护的）
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method: methods){
            System.out.println(method);
        }

        System.out.println("~~~~~~~~~~~");
        //获取共有方法，更上面是有区别的
        methods = clazz.getMethods();
        for(Method method: methods) {
            System.out.println(method);
        }

    }
}
