/**
 * Created by alex on 07.07.2015.
 *
 */

public class HeartTestDrive {
    public static void main(String[] args) {
        HeartModelInterface model = new HeartModel();
        ControllerInterface controller = new HeartController(model);
    }
}
