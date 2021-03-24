package com.next.interview.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//表示该annotation用在什么地方
@Target(ElementType.TYPE)

//表示annotation可以运行的时候被加载
@Retention(RetentionPolicy.RUNTIME)
public @interface PKBean {
    String value();
}
