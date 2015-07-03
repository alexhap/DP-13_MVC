/**
 * Created by alex on 03.07.2015.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DJView implements BPMObserver, BeatObserver, ActionListener {

    BeatModelInterface model;
    ControllerInterface controller;

    JFrame viewFrame;
    JPanel viewPanel;
    BeatBar beatBar;
    JLabel bpmOutputLabel;

    JFrame controlFrame;
    JPanel controlPanel;
    JLabel bpmLabel;
    JTextField bpmTextField;
    JButton setBPMButton;
    JButton increaseBPMButton;
    JButton decreaseBPMButton;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem startMenuItem;
    JMenuItem stopMenuItem;

    public DJView(BeatModelInterface model, ControllerInterface controller) {
        this.model = model;
        this.controller = controller;
        model.registerObserver((BPMObserver) this);
        model.registerObserver((BeatObserver) this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == setBPMButton) controller.setBPM(Integer.parseInt(bpmTextField.getText()));
        else if (e.getSource() == increaseBPMButton) controller.increaseBPM();
        else if (e.getSource() == decreaseBPMButton) controller.decreaseBPM();
    }

    public void updateBPM() {
        int bpm = model.getBPM();
        bpmOutputLabel.setText(bpm == 0 ? "Offline" : String.format("Current BPM: %d", bpm));
    }

    public void updateBeat() {
        beatBar.setValue(100);
    }

    public void createView() {
        bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
        bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        beatBar = new BeatBar();
        beatBar.setValue(0);

        JPanel bpmPanel = new JPanel(new GridLayout(2, 1));
        bpmPanel.add(beatBar);
        bpmPanel.add(bpmOutputLabel);

        viewPanel = new JPanel(new GridLayout(1, 2));
        viewPanel.add(bpmPanel);

        viewFrame = new JFrame("View");
        viewFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        viewFrame.setSize(100, 80);
        viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
        viewFrame.pack();
        viewFrame.setVisible(true);
    }

    public void createControls() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        startMenuItem = new JMenuItem("Start");
        startMenuItem.addActionListener(e -> controller.start());
        stopMenuItem = new JMenuItem("Stop");
        stopMenuItem.addActionListener(e -> controller.stop());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));

        menu = new JMenu("DJ Control");
        menu.add(startMenuItem);
        menu.add(stopMenuItem);
        menu.add(exit);

        menuBar = new JMenuBar();
        menuBar.add(menu);

        bpmTextField = new JTextField(2);
        bpmLabel = new JLabel("Enter BPM:", SwingConstants.RIGHT);
        bpmLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        setBPMButton = new JButton("Set");
        setBPMButton.setSize(10, 40);
        increaseBPMButton = new JButton(">>");
        decreaseBPMButton = new JButton("<<");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(decreaseBPMButton);
        buttonPanel.add(increaseBPMButton);

        JPanel enterPanel = new JPanel(new GridLayout(1, 2));
        enterPanel.add(bpmLabel);
        enterPanel.add(bpmTextField);

        JPanel insideControlPanel = new JPanel(new GridLayout(3, 1));
        insideControlPanel.add(enterPanel);
        insideControlPanel.add(setBPMButton);
        insideControlPanel.add(buttonPanel);

        controlPanel = new JPanel(new GridLayout(1, 2));
        controlPanel.add(insideControlPanel);

        controlFrame = new JFrame("Control");
        controlFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        controlFrame.setSize(100, 80);
        controlFrame.setJMenuBar(menuBar);
        controlFrame.getRootPane().setDefaultButton(setBPMButton);
        controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);
        controlFrame.pack();
        controlFrame.setVisible(true);
    }

    public void setStartMenuItemState(boolean state) {
        startMenuItem.setEnabled(state);
    }

    public void setStopMenuItemState(boolean state) {
        stopMenuItem.setEnabled(state);
    }
}
