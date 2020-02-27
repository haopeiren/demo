package hpr.test.gui;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author haopeiren
 * @since 2020/2/27
 */
public class Temp
{
    public static void main(String[] args) throws Exception
    {
        Random random = new Random();
        while(true)
        {
            int current = random.nextInt(10);
            if (current >= 9)
            {
                System.out.println(current);
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }

    }
}
