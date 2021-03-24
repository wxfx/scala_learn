package java_study;

public class GenericFruit {


    public static void main(String[] args) {
        Apple apple = new Apple();
        Person person = new Person();


        GenerateTest<Fruit> fruitGenerateTest = new GenerateTest<Fruit>();
        fruitGenerateTest.show_1(apple);
        //这里会报错
        //fruitGenerateTest.show_1(person);

        //使用这两个方法都可以成功
        fruitGenerateTest.show_2(apple);
        fruitGenerateTest.show_2(person);


        //使用这两个方法都可以成功
        fruitGenerateTest.show_3(apple);
        fruitGenerateTest.show_3(person);

        /*
        通过show_2和show_3可以总结得出泛型方法的泛型可以与泛型类相同也可以不同
         */
    }
}

class Fruit{
    public String toString(){
        return "fruit";
    }
}

class Apple extends Fruit{
    public String toString(){
        return "apple";
    }
}

class Person{
    public String toString(){
        return "Person";
    }
}

class GenerateTest<T>{
    public void show_1(T t){
        System.out.println(t.toString());
    }

    public <E> void show_3(E t){
        System.out.println(t.toString());
    }

    public <T> void show_2(T t){
        System.out.println(t.toString());
    }
}
