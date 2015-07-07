/**
 * Created by alex on 04.07.2015.
 *
 */

import javax.swing.*;

public class BeatBar extends JProgressBar implements Runnable {
//    JProgressBar progressBar;
    Thread thread;

    public BeatBar() {
        thread = new Thread(this);
        setMaximum(100);
        thread.start();
    }

    public void run() {
        while(true) {
            setValue(getValue() * 3 / 5);
            repaint();
            try {
                Thread.sleep(50);
            } catch (Exception e) {}
        }
    }
}