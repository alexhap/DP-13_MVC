/**
 * Created by alex on 03.07.2015.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DJView implements ActionListener, BeatObserver, BPMObserver {

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

    public DJView(ControllerInterface controller, BeatModelInterface model) {
        this.controller = controller;
        this.model = model;
        model.registerObserver((BeatObserver) this);
        model.registerObserver((BPMObserver) this);
    }

    public void createView() {
        viewPanel = new JPanel(new GridLayout(1, 2));
        viewFrame = new JFrame("View");
        viewFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        viewFrame.setSize(100, 80);

        bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);

        beatBar = new BeatBar();
        beatBar.setValue(0);

        JPanel bpmPanel = new JPanel(new GridLayout(2, 1));
        bpmPanel.add(beatBar);
        bpmPanel.add(bpmOutputLabel);

        viewPanel.add(bpmPanel);

        viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
        viewFrame.pack();
        viewFrame.setVisible(true);
    }

    public void createControls() {

        controlFrame = new JFrame("Control");
        controlFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        controlFrame.setSize(100, 80);

        controlPanel = new JPanel(new GridLayout(1, 2));

        menuBar = new JMenuBar();
        menu = new JMenu("DJ Control");

        startMenuItem = new JMenuItem("Start");
        menu.add(startMenuItem);
        startMenuItem.addActionListener(e -> controller.start());
        stopMenuItem = new JMenuItem("Stop");
        menu.add(stopMenuItem);
        stopMenuItem.addActionListener(e -> controller.stop());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        menu.add(exit);

        menuBar.add(menu);
        controlFrame.setJMenuBar(menuBar);

        bpmTextField = new JTextField(2);
        bpmLabel = new JLabel("Enter BPM:", SwingConstants.RIGHT);

        setBPMButton = new JButton("Set");
        setBPMButton.setSize(10, 40);
        increaseBPMButton = new JButton(">>");
        decreaseBPMButton = new JButton("<<");

        setBPMButton.addActionListener(this);
        increaseBPMButton.addActionListener(this);
        decreaseBPMButton.addActionListener(this);

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

        controlPanel.add(insideControlPanel);

        bpmLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == setBPMButton)
            controller.setBPM(Integer.parseInt(bpmTextField.getText()));
        else if (e.getSource() == increaseBPMButton)
            controller.increaseBPM();
        else if (e.getSource() == decreaseBPMButton)
            controller.decreaseBPM();
    }

    public void updateBPM() {
        if (model != null) {
            int bpm = model.getBPM();
            bpmOutputLabel.setText(bpm == 0 ? "Offline" : String.format("Current BPM: %d", bpm));
        }
    }

    public void updateBeat() {
        if (beatBar != null) {
            beatBar.setValue(100);
        }
    }

}
