package com.next.interview.framework.utils;

import com.next.interview.framework.annotation.PKField;
import com.next.interview.framework.annotation.PKBean;

import java.lang.reflect.Field;

public class Tools {
    /*
        根据注解获取表名
     */
    public static String getTable(Class<?> clazz){
        String tableName = "";
        PKBean pkBean = clazz.getAnnotation(PKBean.class);
        if (pkBean != null){
            tableName = pkBean.value();
        }else {
            tableName = clazz.getName();
        }

        return tableName;
    }

    /*
        根据注解获取属性名称
     */
    public static String getColumn(Field field){
        String column = "";
        PKField pkField = field.getAnnotation(PKField.class);

        if (pkField != null){
            column =  pkField.value();
        }else {
            column = field.getName();
        }
       return column;
    }

    /*
        获取get方法名
     */
    public static String getMethod(Field field){
        String fieldName = field.getName();
        //id => getId name => getName
        String name = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        return "get" + name;

    }
}
