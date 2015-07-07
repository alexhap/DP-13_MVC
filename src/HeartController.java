/**
 * Created by alex on 07.07.2015.
 *
 */

public class HeartController implements ControllerInterface {

    private HeartModelInterface model;
    private DJView view;

    public HeartController(HeartModelInterface model) {
        this.model = model;
        view = new DJView(this, new HeartAdapter(model));
        view.createView();
        view.createControls();
        view.setStartMenuItemState(true);
        view.setStopMenuItemState(false);
    }

    public void start() {

    }

    public void stop() {

    }

    public void increaseBPM() {

    }

    public void decreaseBPM() {

    }

    public void setBPM(int bpm) {

    }
}
