/**
 * Created by alex on 07.07.2015.
 *
 */

public interface HeartModelInterface {
    int getHeartRate();
    void registerObserver(BeatObserver obs);
    void registerObserver(BPMObserver obs);
    void removeObserver(BeatObserver obs);
    void removeObserver(BPMObserver obs);
}
