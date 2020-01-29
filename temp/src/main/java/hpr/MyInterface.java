package hpr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haopeiren
 * @since 2020/1/29
 */
public interface MyInterface
{
    void sayHello();

    public int add(int a, int b);

    default void sayHello(String name)
    {
        System.out.println("hello :" + name);
    }

    public static final String name = "zhangsan";

    static void helloWorld()
    {
        System.out.println("hello world");
    }
}

class MyInterfaceImpl2 implements MyInterface
{

    @Override
    public void sayHello()
    {

    }

    @Override
    public int add(int a, int b)
    {
        return 0;
    }
}

class MyInterfaceImpl implements MyInterface
{

    public static void main(String[] args)
    {

        MyInterfaceImpl temp = new MyInterfaceImpl();

        String name = MyInterface.name;
        MyInterface.helloWorld();




        MyInterface impl = new MyInterfaceImpl();
        MyInterface impl2 = new MyInterfaceImpl2();
        impl.sayHello();
        impl2.sayHello();
        temp.sayHello();

        temp.sayHello("zhangsan");



        ArrayList list = new ArrayList();
        list.add("zhangsan");

    }

    @Override
    public void sayHello()
    {
        System.out.println("say hello");
    }

    @Override
    public int add(int a, int b)
    {
        int result = a + b;
        return result;
    }

    public int add(int a, int b, int c)
    {
        return a + b + c;
    }

    private void test(String name)
    {
        System.out.println(name);
    }
}
