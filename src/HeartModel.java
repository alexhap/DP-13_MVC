import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by alex on 07.07.2015.
 *
 */

public class HeartModel implements HeartModelInterface, Runnable {

    private List<BPMObserver> bpmObservers = new ArrayList<>();
    private List<BeatObserver> beatObservers = new ArrayList<>();
    int bpm = 65;
    int time = 1000;
    Random random = new Random(System.currentTimeMillis());
    Thread thread;

    public HeartModel() {
        thread = new Thread(this);
        thread.start();
    }

    public int getHeartRate() {
        return 60000 / time;
    }

    public void registerObserver(BeatObserver obs) {
        beatObservers.add(obs);
    }

    public void registerObserver(BPMObserver obs) {
        bpmObservers.add(obs);
    }

    public void removeObserver(BeatObserver obs) {
        beatObservers.remove(obs);
    }

    public void removeObserver(BPMObserver obs) {
        bpmObservers.remove(obs);
    }

    public void notifyBPMObservers() {
        for (BPMObserver obs : bpmObservers)
            obs.updateBPM();
    }

    public void notifyBeatObservers() {
        for (BeatObserver obs : beatObservers)
            obs.updateBeat();
    }

    public void run() {
        int lastRate = Integer.MAX_VALUE;
        while (true) {
            int change = random.nextInt(10) * (random.nextInt(2) == 0 ? -1 : 1);
            int rate = 60000 / (time + change);
            if (rate <= 120 && rate >= 50) {
                time += change;
                notifyBeatObservers();
                if (rate != lastRate) {
                    lastRate = rate;
                    notifyBPMObservers();
                }
            }
            try {
                Thread.sleep(time);
            } catch (Exception e) {}
        }
    }
}
