package hpr.test.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author haopeiren
 * @since 2020/2/27
 */
public class FoodChoose
{
    private static final int MAX_WIDTH = 500;

    private static final int MAX_HEIGHT = MAX_WIDTH + 100;

    private static final int BUTTON_WIDTH = 50;

    private static final int BUTTON_HEIGHT = 50;

    private static final java.util.List<String> excludeName = new ArrayList<>();

    private static final String START_PIC_NAME = "INIT";

    private static final String BUTTON_LEFT_PIC_NAME = "BUTTON_LEFT";

    private static final String BUTTON_RIGHT_PIC_NAME = "BUTTON_RIGHT";

    public FoodChoose(String[] args)
    {
        init(args);
    }

    public static void main(String[] args)
    {
        new FoodChoose(args);
    }
    private void init(String[] args)
    {
//        String picDir = args[1];
        String picDir = "C:\\Users\\haopeiren-pc\\Desktop\\demo\\pictures";
        JFrame jFrame = new JFrame("今天吃什么？");
        jFrame.setResizable(false);
        jFrame.setSize(MAX_WIDTH, MAX_HEIGHT);
        jFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, MAX_WIDTH, MAX_HEIGHT);




        java.util.List<ImageIcon> imageList = getImageList(picDir);

        ImageIcon startPic = imageList.stream()
                .filter(e -> e.getDescription().contains(START_PIC_NAME))
                .findFirst().get();

        ImageIcon buttonLeftPic = imageList.stream()
                .filter(e -> e.getDescription().contains(BUTTON_LEFT_PIC_NAME))
                .findFirst().get();

        ImageIcon buttonRightPic = imageList.stream()
                .filter(e -> e.getDescription().contains(BUTTON_RIGHT_PIC_NAME))
                .findFirst().get();

        JLabel label = new JLabel("", JLabel.CENTER);
        label.setIcon(startPic);
        label.setBounds(0, 0, MAX_WIDTH, MAX_WIDTH);
        label.setFont(FontUtil.getDefaultFont());
        panel.add(label);

        imageList.remove(startPic);
        imageList.remove(buttonLeftPic);
        imageList.remove(buttonRightPic);

        ChooseFoodThread thread = new ChooseFoodThread(imageList, label);

        int buttonStartX = (MAX_WIDTH / 2 - BUTTON_WIDTH) / 2;
        int buttonStopX = buttonStartX + MAX_WIDTH / 2;

        int buttonHeight = MAX_WIDTH + 11;
//        int buttonHeight = MAX_WIDTH + ((MAX_HEIGHT - MAX_WIDTH - BUTTON_HEIGHT) / 2);

        JButton buttonStart = new JButton("开始");
        buttonStart.setBounds(buttonStartX, buttonHeight, BUTTON_WIDTH, BUTTON_HEIGHT);
        buttonStart.addActionListener(e -> thread.startRunning());
        buttonStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonStart.setIcon(buttonLeftPic);

        JButton buttonStop = new JButton("停止");
        buttonStop.setBounds(buttonStopX, buttonHeight, BUTTON_WIDTH, BUTTON_HEIGHT);
        buttonStop.addActionListener(e -> thread.stopRunning());
        buttonStop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonStop.setIcon(buttonRightPic);
        panel.add(buttonStart);
        panel.add(buttonStop);

        jFrame.add(panel);
        thread.start();
        jFrame.setVisible(true);
    }

    private java.util.List<ImageIcon> getImageList(String pictureDir)
    {
        File root = new File(pictureDir);
        File[] foods = root.listFiles();
        if (foods == null)
        {
            System.out.println("get picture failed");
            return null;
        }
        System.out.println("picture count : " + foods.length);
        java.util.List<ImageIcon> imageIconList = new java.util.ArrayList<>();
        Arrays.stream(foods).forEach(current ->
        {
            try {
                String fileName = current.getName();
                String filePath = current.getCanonicalPath();
                String[] fileNames = fileName.split("-");
                String foodName = fileNames[0];
                int size = 1;
                if (fileNames.length > 1 && false)
                {
                    size = Integer.parseInt(fileNames[fileNames.length - 1]);
                    System.out.println("got a weight food : " + foodName + ", weight : " + size);
                }
                for(int i = 0; i < 1; i++)
                {
                    ImageIcon imageIcon = new ImageIcon(filePath, foodName);
                    imageIconList.add(imageIcon);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return imageIconList;
    }

    private static class ChooseFoodThread extends Thread
    {
        private java.util.List<ImageIcon> imageList;

        private JLabel label;

        private volatile boolean running;

        private Random random = new Random();

        public ChooseFoodThread(java.util.List<ImageIcon> imageList, JLabel jLabel)
        {
            this.imageList = imageList;
            this.label = jLabel;
        }

        @Override
        public void run()
        {
            while(true)
            {
//                if (running == null)
//                {
//                    continue;
//                }
                if (running)
                {
                    refreshFood();
                    sleepMilliSec(100);
                }
//                else
//                {
//                    int sleepMilli = 1;
//                    int index = 1;
//                    while (sleepMilli < 2000)
//                    {
//                        refreshFood();
//                        sleepMilliSec(sleepMilli);
//                        sleepMilli = sleepMilli + 100 * index;
//                        index++;
//                    }
//                    running = null;
//                }
            }
        }

        private void refreshFood()
        {
            int currentIndex = random.nextInt(imageList.size());
            ImageIcon currentFood = imageList.get(currentIndex);
            label.setIcon(currentFood);
        }

        private void sleepMilliSec(int milliSec)
        {
            try {
                TimeUnit.MILLISECONDS.sleep(milliSec);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private boolean isRunning()
        {
            return running;
        }

        private void startRunning()
        {
            running = true;
        }

        private void stopRunning()
        {
            running = false;
        }
    }
}
