package hpr;

/**
 * @author haopeiren
 * @since 2020/1/29
 */
public class Temp
{
    public Temp()
    {
        System.out.println("no param constractor");
    }

    public Temp(String name)
    {
        System.out.println("hello : " + name);
    }

    public static void main(String[] args)
    {
        Temp2 t2 = new Temp2();
    }
}

class Temp2 extends Temp
{
    public Temp2()
    {
        System.out.println("temp2 no param constructor");
    }

    public Temp2(String name2)
    {
        System.out.println("hello2 : " + name2);
    }
}
