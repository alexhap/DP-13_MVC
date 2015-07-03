/**
 * Created by alex on 04.07.2015.
 *
 */

public class BeatController implements ControllerInterface {

    BeatModelInterface model;
    DJView view;

    public BeatController(BeatModelInterface model) {
        this.model = model;
        view = new DJView(model, this);
        view.createView();
        view.createControls();
        view.setStartMenuItemState(true);
        view.setStopMenuItemState(false);
        model.initialize();
    }

    public void start() {
        model.on();
        view.setStartMenuItemState(false);
        view.setStopMenuItemState(true);
    }

    public void stop() {
        model.off();
        view.setStartMenuItemState(true);
        view.setStopMenuItemState(false);
    }

    public void increaseBPM() {
        model.setBPM(model.getBPM() + 1);
    }

    public void decreaseBPM() {
        model.setBPM(model.getBPM() - 1);
    }

    public void setBPM(int bpm) {
        model.setBPM(bpm);
    }

    public static void main(String[] args) {
        BeatModel model = new BeatModel();
        BeatController controller = new BeatController(model);
    }
}
