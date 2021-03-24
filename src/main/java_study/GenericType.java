package java_study;

import javafx.scene.paint.Stop;
import jdk.nashorn.internal.ir.CallNode;
import scala.None;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenericType {
    public static void main(String[] args) {
        //不带泛型
        //Exception in thread "main" java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
        //notGenericTypeFun();

        //带泛型
        //GenericTypeFun();

        //泛型类传入泛型类型测试
        //GenericClass<Integer> integerGenericClass = new GenericClass<Integer>(123456);
        GenericClass<String> stringGenericClass = new GenericClass<String>("我终于搞懂了泛型");

        //System.out.println("泛型整形：key is " + integerGenericClass.getKey());
        //System.out.println("泛型字符串形：key is " + stringGenericClass.getKey());

        //泛型类不传入泛型类型
        //GenericClass genericClass = new GenericClass("123");
        //GenericClass genericClass1 = new GenericClass(123);
        //GenericClass genericClass2 = new GenericClass(12.3);
        //GenericClass genericClass3 = new GenericClass(false);

        //System.out.println("key is " + genericClass.getKey());
        //System.out.println("key is " + genericClass1.getKey());
        //System.out.println("key is " + genericClass2.getKey());
        //System.out.println("key is " + genericClass3.getKey());

        //泛型接口测试
        //FruitGenerator1 fruitGenerator1 = new FruitGenerator1();
        //System.out.println(fruitGenerator1.next());

        //泛型通配符
        GenericClass<Integer> integerGenericClass = new GenericClass<Integer>(123);
        GenericClass<Number> numberGenericClass = new GenericClass<Number>(456);

        //这个会报错，Generic<Integer>不能被看作是Generic<Number>的子类
        //showKeyValue(integerGenericClass);
        //showKeyValue(numberGenericClass);

        //showKeyValue1(integerGenericClass);
        //showKeyValue1(numberGenericClass);

        //泛型方法
        //整形
        //Integer integer = showKeyName(integerGenericClass);
        //System.out.println("泛型方法 integer:" + integer);
        //String string = showKeyName(stringGenericClass);
        //System.out.println("泛型方法 string:" + string);

        //泛型方法与可变参数
        //printMsg("111", "222", "33", 12, 5.5, "hi");

        //静态方法与泛型
        //printMsg_1("123", "13333");

        //泛型上下边界
        GenericClass<String> stringGeneric = new GenericClass<String>("123");
        GenericClass<Integer> intGeneric = new GenericClass<Integer>(1);
        GenericClass<Float> floatGeneric = new GenericClass<Float>(1.1f);
        GenericClass<Double> doubleGeneric = new GenericClass<Double>(1.1);

        //这里报错，因为String不是Number的子类型
        //showKeyValue2(stringGeneric);
        //showKeyValue2(intGeneric);
        //showKeyValue2(floatGeneric);
        //showKeyValue2(doubleGeneric);

    }
    public static void notGenericTypeFun(){
        List arrayList = new ArrayList();
        arrayList.add("aaaa");
        arrayList.add(100);

        for (int i = 0; i < arrayList.size(); i++){
            String item = (String)arrayList.get(i);
            System.out.println("泛型测试item = " + item);
        }
    }

    public static void GenericTypeFun(){
        List<String> stringArrayList = new ArrayList<String>();
        //编译阶段，编译器报错
        //stringArrayList.add(100);
        List<Integer> integerArrayList = new ArrayList<Integer>();

        Class classStringArrayList = stringArrayList.getClass();
        Class classIntegerArrayList = integerArrayList.getClass();

        if (classStringArrayList.equals(classIntegerArrayList)){
            //输出 class java.util.ArrayList，证明泛型只有在编译阶段有效，不会进入运行阶段
            System.out.println(classIntegerArrayList);
            System.out.println("泛型测试，类型相同");
        }
    }

    public static void showKeyValue(GenericClass<Number> obj){
        System.out.println("泛型通配符, key value is " + obj.getKey());
    }
    //?代表的是具体实参
    public static void showKeyValue1(GenericClass<?> obj){
        System.out.println("泛型通配符, key value is " + obj.getKey());
    }

    public static <k> k showKeyName(GenericClass<k> container){
        System.out.println("container key: " + container.getKey());
        k key = container.getKey();
        return key;
    }

    //泛型方法与可变参数
    public static <T> void printMsg(T... args){
        for(T t: args){
            System.out.println("t is " + t);
        }
    }
    //静态方法与泛型，注：静态方法引用泛型时必须将此方法定义成泛型方法
    // Error:(115, 35) java: 找不到符号
    //  符号:   类 T
    //  位置: 类 java_study.GenericType
    //public static  void printMsg_1(T... args){
    //    System.out.println("123");
    //}

    //泛型上下边界
    public static  void showKeyValue2(GenericClass<? extends Number> obj){
        System.out.println("key value is " + obj.getKey());
    }

}

class FruitGenerator<k> implements GenericInterface<k>{

    public k next() {
        return null;
    }
}

class FruitGenerator1 implements GenericInterface<String>{
    private String[] fruits = new String[]{"apple", "banana", "pear"};
    public String next() {
        Random random = new Random();
        return fruits[random.nextInt(3)];
    }
}

