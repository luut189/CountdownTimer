import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class Frame extends JFrame {

    private JPanel containter;
    private JLabel counter;
    private SwingWorker<Void, Void> worker;

    private LocalDateTime dateTime;
    private long hour;
    private long minute;
    private long second;

    public Frame(LocalDateTime dateTime, long secondsLeft, int width, int height) {
        this.dateTime = dateTime;
        getTime();
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

    public void getTime() {
        long secondsLeft = Duration.between(LocalDateTime.now(), dateTime).toSeconds();
        hour = TimeUnit.SECONDS.toHours(secondsLeft);
        minute = TimeUnit.SECONDS.toMinutes(secondsLeft) - hour*60;
        second = secondsLeft - hour*3600 - minute*60;
    }

    public void updateTime() {
        StringBuilder sb = new StringBuilder();

        if(hour < 10) sb.append("0" + hour + ":");
        else sb.append(hour + ":");

        if(minute < 10) sb.append("0" + minute + ":");
        else sb.append(minute + ":");

        if(second < 10) sb.append("0" + second);
        else sb.append(second);

        counter.setText(sb.toString());
    }

    public void setupCounter() {
        worker = new SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while(true) {
                    getTime();
                    updateTime();
                    if(hour == 0 && minute == 0 && second == 0) break;
                }
                return null;
            }

            @Override
            protected void done() {
                super.done();
                counter.setForeground(Color.red);
                counter.setText("Chet me may roi");
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
