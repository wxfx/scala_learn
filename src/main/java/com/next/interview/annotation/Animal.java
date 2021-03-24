package com.next.interview.annotation;

import com.next.interview.framework.annotation.PKBean;
import com.next.interview.framework.annotation.PKField;

@PKBean("_animal")
public class Animal {
    private Integer id;
    @PKField("_name")
    private String name;
    private String sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
