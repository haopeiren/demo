package hpr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haopeiren
 * @since 2020/1/29
 */
public abstract class AbstractClass
{
    abstract void test();

    public int test2(int a, int b)
    {
        List list = new ArrayList();
        return a+b;
    }
}



class MyClassImpl extends AbstractClass implements Inter1, Inter2
{

    public static void main(String[] args)
    {
        MyClassImpl impl = new MyClassImpl();
        AbstractClass absImpl = new MyClassImpl();
        Inter1 inter1 = new MyClassImpl();
        Inter2 inter2 = new MyClassImpl();
    }
    @Override
    void test()
    {
        System.out.println("");
    }

    @Override
    public void inter1()
    {
        System.out.println("inter1");
    }

    @Override
    public void inter2()
    {
        System.out.println("inter2");
    }
}


interface Inter1
{
    void inter1();
}

interface Inter2
{
    void inter2();
}