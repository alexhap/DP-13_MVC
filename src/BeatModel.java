/**
 * Created by alex on 03.07.2015.
 *
 */

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

public class BeatModel implements BeatModelInterface, MetaEventListener {

    private Sequencer sequencer;
    List<BPMObserver> bpmObservers = new ArrayList<>();
    List<BeatObserver> beatObservers = new ArrayList<>();
    int bpm = 90;
    Sequence sequence;
    Track track;

    public void initialize() {
        setUpMidi();
        buildTrackAndStart();
    }

    public void on() {
        sequencer.start();
        setBPM(bpm);
    }

    public void off() {
        setBPM(0);
        sequencer.stop();
    }

    public void setBPM(int bpm) {
        this.bpm = bpm;
        sequencer.setTempoInBPM(bpm);
        notifyBPMObservers();
    }

    public int getBPM() {
        return this.bpm;
    }

    public void registerObserver(BeatObserver o) {
        beatObservers.add(o);
    }

    public void removeObserver(BeatObserver o) {
        beatObservers.remove(o);
    }

    public void registerObserver(BPMObserver o) {
        bpmObservers.add(o);
    }

    public void removeObserver(BPMObserver o) {
        bpmObservers.remove(o);
    }

    private void notifyBPMObservers() {
        for (BPMObserver obs: bpmObservers)
            obs.updateBPM();
    }

    private void notifyBeatObservers() {
        for (BeatObserver obs: beatObservers)
            obs.updateBeat();
    }

    void beatEvent() {
        notifyBeatObservers();
    }

    public void meta(MetaMessage meta) {
        if (meta.getType() == 47) {
            beatEvent();
            sequencer.start();
            setBPM(getBPM());
        }
    }

    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(getBPM());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildTrackAndStart() {
        int[] trackList = {35, 0, 46, 0};
        sequence.deleteTrack(null);
        track = sequence.createTrack();
        makeTracks(trackList);
        track.add(makeEvent(192, 9, 1, 0, 4));
        try {
            sequencer.setSequence(sequence);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void makeTracks(int[] trackList) {
        for (int i = 0; i < trackList.length; i++) {
            int key = trackList[i];
            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return event;
    }
}
