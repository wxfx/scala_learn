package com.next.interview.reflect;

public class Student {
    private String name;
    private String sex;
    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }

    public Student(){
        System.out.println("这是Student的无参构造器");
    }

    public Student(String name, String sex, Integer age){
        this.name = name;
        this.sex = sex;
        this.age = age;
        System.out.println("这是Student的有参构造器");
    }

    public Student(String name){
        this.name = name;
        System.out.println("这是Student的有参构造器:name");
    }

}
