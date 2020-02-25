package hpr.test.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author haopeiren
 * @since 2020/2/25
 */
public class Application
{
    private static final int MAIN_WIDTH = 500;

    private static final int MAIN_HEIGHT = 300;

    private static final Font DEFAULT_FONT = new Font("宋体",Font.PLAIN,30);

    private static final String YES_MESSAGE = "恭喜你获得优秀老公一枚";

    private static String[] NO_MESSAGE= new String[]{
            "工资上交", "家务全包","千依百顺", "唯命是从", "不发脾气", "随时待命", "难产保大"
    };

    public static void main(String[] args)
    {
        int size = NO_MESSAGE.length;
        AtomicInteger index = new AtomicInteger(0);
        JFrame frame = new JFrame("真心话");
        frame.setSize(MAIN_WIDTH, MAIN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new MyWindowListener(frame));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(MAIN_WIDTH, MAIN_HEIGHT);
        panel.setVisible(true);

        JLabel label = new JLabel("你愿意嫁给郝先生", JLabel.CENTER);
        label.setBounds(0, 30, MAIN_WIDTH, 30);
        label.setFont(DEFAULT_FONT);
        panel.add(label);

        JLabel label2 = new JLabel("做他的妻子吗？", JLabel.CENTER);
        label2.setBounds(0, 80, MAIN_WIDTH, 30);
        label2.setFont(DEFAULT_FONT);
        panel.add(label2);

        JButton buttonYes = new JButton("愿意");
        int buttonY = 150;
        int buttonWidth = 160;
        int buttonHeight = 80;

        int buttonYesX = (MAIN_WIDTH - buttonWidth) / 2;
        buttonYes.setSize(buttonWidth, buttonHeight);
        buttonYes.setFont(DEFAULT_FONT);
        buttonYes.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(frame, YES_MESSAGE);
                JOptionPane.showMessageDialog(frame, "现在可以关掉啦");
                MyWindowListener.setGranted();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        buttonYes.setBounds(buttonYesX, buttonY, buttonWidth, buttonHeight);

        JButton buttonNo = new JButton("不愿意");
        int buttonNoX = buttonYesX + MAIN_WIDTH / 2;
        buttonNo.setSize(buttonWidth, buttonHeight);
        buttonNo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(frame, NO_MESSAGE[index.getAndIncrement() % size]);
            }
        });
        buttonNo.setMargin(new Insets(0, 0, 0, 0));
        buttonNo.setBounds(400, 200, 50, 20);

        panel.add(buttonYes);
        panel.add(buttonNo);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static class MyWindowListener implements WindowListener
    {
        private JFrame jFrame;

        private static boolean closeGranted = false;

        private MyWindowListener(JFrame frame)
        {
            jFrame = frame;
        }

        private static void setGranted()
        {
            closeGranted = true;
        }

        private static boolean getGranted()
        {
            return closeGranted;
        }


        /**
         * Invoked the first time a window is made visible.
         *
         * @param e
         */
        @Override
        public void windowOpened(WindowEvent e)
        {

        }

        /**
         * Invoked when the user attempts to close the window
         * from the window's system menu.
         *
         * @param e
         */
        @Override
        public void windowClosing(WindowEvent e)
        {
            if (!getGranted())
            {
                JOptionPane.showMessageDialog(jFrame, "想关掉？没门！");
            }
        }

        /**
         * Invoked when a window has been closed as the result
         * of calling dispose on the window.
         *
         * @param e
         */
        @Override
        public void windowClosed(WindowEvent e)
        {

        }

        /**
         * Invoked when a window is changed from a normal to a
         * minimized state. For many platforms, a minimized window
         * is displayed as the icon specified in the window's
         * iconImage property.
         *
         * @param e
         * @see Frame#setIconImage
         */
        @Override
        public void windowIconified(WindowEvent e)
        {

        }

        /**
         * Invoked when a window is changed from a minimized
         * to a normal state.
         *
         * @param e
         */
        @Override
        public void windowDeiconified(WindowEvent e)
        {

        }

        /**
         * Invoked when the Window is set to be the active Window. Only a Frame or
         * a Dialog can be the active Window. The native windowing system may
         * denote the active Window or its children with special decorations, such
         * as a highlighted title bar. The active Window is always either the
         * focused Window, or the first Frame or Dialog that is an owner of the
         * focused Window.
         *
         * @param e
         */
        @Override
        public void windowActivated(WindowEvent e)
        {

        }

        /**
         * Invoked when a Window is no longer the active Window. Only a Frame or a
         * Dialog can be the active Window. The native windowing system may denote
         * the active Window or its children with special decorations, such as a
         * highlighted title bar. The active Window is always either the focused
         * Window, or the first Frame or Dialog that is an owner of the focused
         * Window.
         *
         * @param e
         */
        @Override
        public void windowDeactivated(WindowEvent e)
        {

        }
    }
}
