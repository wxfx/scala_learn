package com.next.interview.reflect;

import jdk.nashorn.internal.ir.CallNode;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class IntrospectorApp {
    @Test
    public void test02() throws Exception{
        Student student = new Student();
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("age", Student.class);
        System.out.println("~~~~~~");

        Method setMethod = propertyDescriptor.getWriteMethod();
        setMethod.invoke(student, 30);
        System.out.println(student.getAge());
        System.out.println("~~~~~~");

        Method getMethod = propertyDescriptor.getReadMethod();
        System.out.println(getMethod.invoke(student));
    }
    @Test
    public void test01() throws Exception{
        BeanInfo beanInfo = Introspector.getBeanInfo(Student.class);
        System.out.println(beanInfo);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor: propertyDescriptors){
            System.out.println(propertyDescriptor.getName());
        }


    }
}
