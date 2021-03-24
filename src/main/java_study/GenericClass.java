package java_study;

//k的类型统一由外部指定
public class GenericClass<k> {
    //成员变量key的类型为k
    private k key;

    //泛型构造方法形参key的类型也为k
    public GenericClass(k key){
        this.key = key;
    }

    public k getKey() {
        return key;
    }
}
