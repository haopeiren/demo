import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * @author haopeiren
 * @since 2020/2/15
 */
public class MapleStory
{
    public static void main(String[] args)
    {
        run();
    }

    @SneakyThrows
    private static void run()
    {
        Robot robot = new Robot();
        int a = 0;
        while (a < 100)
        {
            robot.setAutoWaitForIdle(true);
//            robot.keyPress(KeyEvent.VK_S);
            robot.keyPress(InputEvent.BUTTON1_MASK);
            System.out.println("input s " + a);
            robot.delay(1000);

            robot.keyRelease(InputEvent.BUTTON1_MASK);
//            robot.keyRelease(KeyEvent.VK_S);
            a++;
            Thread.sleep(1000);
        }
    }
}
