package com.next.interview.reflect;

import org.junit.Test;

import java.lang.reflect.Method;

/*
    1) 如何获取到执行的Method
    2) 如何动态调用Method
 */
public class MethodApp{
   @Test
   public void test02() throws Exception {
      Class<?> clazz = Class.forName("com.next.interview.reflect.Student");
      Object object = clazz.newInstance();

      Method setSexMethod = clazz.getDeclaredMethod("setSex", String.class);
      setSexMethod.invoke(object, "中性");

      System.out.println(object);

      Method getSexMethod = clazz.getDeclaredMethod("getSex");
      Object sex = getSexMethod.invoke(object);
      System.out.println(sex);


   }
      @Test
   public void test01() throws Exception{
      Class<?> clazz = Class.forName("com.next.interview.reflect.Student");

      Method[] methods = clazz.getMethods();
      for (Method method: methods){
         Class<?>[] parameterTypes = method.getParameterTypes();
         //System.out.println(method.getName());
         //将有参数的方法遍历出来，没有参数的方法就无法进去
         for(Class<?> parameterType: parameterTypes){
            System.out.println(method.getName() + " 和 " + parameterType);
         }
      }
   }
}
