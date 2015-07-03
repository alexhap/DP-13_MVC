/**
 * Created by alex on 04.07.2015.
 *
 */

import javax.swing.*;

public class BeatBar extends JProgressBar implements Runnable {

    Thread thread;

    public BeatBar() {
        thread = new Thread(this);
        setMaximum(100);
        thread.start();
    }

    public void run() {
        while (true) {
            setValue((int) (getValue() * 0.75));
            repaint();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        }
    }
}
