package com.next.interview.annotation;

import com.next.interview.framework.annotation.PKField;
import com.next.interview.framework.annotation.PKBean;
import org.junit.Test;

import java.lang.reflect.Field;

public class AnnotationApp {
    @Test
    public void test02() throws Exception {
        Class<?> clazz = Class.forName("com.next.interview.annotation.Animal");
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields){
            PKField pkField = field.getAnnotation(PKField.class);
            if(pkField != null){
                System.out.println(field.getName() + " ~~~ " + pkField.value());
            }


        }
    }
        @Test
    public void test01() throws Exception{
        Class<?> clazz = Class.forName("com.next.interview.annotation.Animal");

        //判断该Class上是否有指定的注解出现
        boolean isAnnotation = clazz.isAnnotationPresent(PKBean.class);
        System.out.println(isAnnotation);

        if(isAnnotation){
            PKBean pkBean = clazz.getAnnotation(PKBean.class);
            if (pkBean != null){
                System.out.println(pkBean);
                System.out.println(pkBean.value());
            }

        }

    }
}
