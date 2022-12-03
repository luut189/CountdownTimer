import java.awt.GridBagLayout;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class Frame extends JFrame {

    private JPanel containter;
    private JLabel counter;
    private SwingWorker<Void, Void> worker;

    private long hour;
    private long minute;
    private long second;

    public Frame(long secondsLeft, int width, int height) {
        hour = TimeUnit.SECONDS.toHours(secondsLeft);
        minute = TimeUnit.SECONDS.toMinutes(secondsLeft) - hour*60;
        second = secondsLeft - hour*3600 - minute*60;
        setupCounter();

        containter = new JPanel();
        containter.setLayout(new GridBagLayout());
        containter.setPreferredSize(new Dimension(width, height));
        containter.setBackground(Color.black);
        containter.add(counter);

        setTitle("Countdown");
        add(containter);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateTime() {
        counter.setText(hour + ":" + minute + ":" + second);
    }

    public void setupCounter() {
        worker = new SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while(hour != 0 || minute != 0 || second != 0) {
                    if(second == 0) {
                        second = 60;
                        minute--;
                        if(minute == 0) {
                            minute = 60;
                            hour--;
                        }
                    }
                    second--;
                    updateTime();
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        worker.execute();

        counter = new JLabel();
        counter.setText(hour + ":" + minute + ":" + second);
        counter.setForeground(Color.white);
        counter.setFont(new Font("Consolas", Font.BOLD, 120));
        counter.setAlignmentY(CENTER_ALIGNMENT);
        counter.setHorizontalAlignment(JLabel.CENTER);
        counter.setVerticalAlignment(JLabel.CENTER);
    }
    
}
