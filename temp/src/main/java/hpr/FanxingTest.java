package hpr;

/**
 * @author haopeiren
 * @since 2020/1/29
 */
public class FanxingTest
{
    public <T> void hello(T param)
    {
        System.out.println("hello : " + param);
    }

    public <T> T max(T a, T b)
    {
        if (a == b)
        {
            return a;
        }
        return b;
    }

    public static void main(String[] args) {
        FanxingTest test = new FanxingTest();
        int max = test.max(1, 2);
        String str = test.max("zhang", "san");
        System.out.println(str);
        System.out.println(1 << 8);
    }
}
