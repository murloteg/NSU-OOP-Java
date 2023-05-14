package ru.nsu.bolotov.view;

import ru.nsu.bolotov.progress.CarFactoryProgress;
import ru.nsu.bolotov.util.UtilConsts;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ApplicationView implements PartOfView, ChangeListener {
    private final JFrame frame;
    private final JLabel delayOfCarcassesLabel;
    private final JSlider carcassesSlider;
    private final JLabel delayOfEnginesLabel;
    private final JSlider enginesSlider;
    private final JLabel delayOfAccessoriesLabel;
    private final JSlider accessoriesSlider;
    private final JLabel delayOfDealersLabel;
    private final JSlider dealersSlider;
    private final JLabel storageProgressLabel;
    private final JLabel queueProgressLabel;
    private final JLabel createdCarsProgressLabel;
    private final CarFactoryProgress factoryProgress;
    private final PropertyChangeSupport support;

    public ApplicationView(CarFactoryProgress factoryProgress) {
        this.factoryProgress = factoryProgress;
        support = new PropertyChangeSupport(this);
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Car Factory");
        frame.setBounds(0, 0, 1200, 800);

        JPanel panel = new JPanel();
        panel.setBackground(Color.CYAN);
        panel.setLayout(null);

        JButton exit = new JButton("Exit");
        exit.setBounds(50, 650, UtilConsts.GUIConsts.DEFAULT_BUTTON_WIDTH, UtilConsts.GUIConsts.DEFAULT_BUTTON_HEIGHT);
        exit.addActionListener(event -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        panel.add(exit);

        carcassesSlider = createSlider(UtilConsts.TimeConsts.MIN_SUPPLIER_DELAY_TIME_MSEC,
                UtilConsts.TimeConsts.MAX_SUPPLIER_DELAY_TIME_MSEC, UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC, 1000);
        carcassesSlider.setBounds(25, 50, 150, 300);
        delayOfCarcassesLabel = new JLabel("Delay of carcasses: " + convertMsecToSec(carcassesSlider.getValue()));
        delayOfCarcassesLabel.setBounds(25, 350, 200, 30);
        panel.add(carcassesSlider);
        panel.add(delayOfCarcassesLabel);

        enginesSlider = createSlider(UtilConsts.TimeConsts.MIN_SUPPLIER_DELAY_TIME_MSEC,
                UtilConsts.TimeConsts.MAX_SUPPLIER_DELAY_TIME_MSEC, UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC, 1000);
        enginesSlider.setBounds(225, 50, 150, 300);
        delayOfEnginesLabel = new JLabel("Delay of engines: " + convertMsecToSec(carcassesSlider.getValue()));
        delayOfEnginesLabel.setBounds(225, 350, 200, 30);
        panel.add(enginesSlider);
        panel.add(delayOfEnginesLabel);

        accessoriesSlider = createSlider(UtilConsts.TimeConsts.MIN_SUPPLIER_DELAY_TIME_MSEC,
                UtilConsts.TimeConsts.MAX_SUPPLIER_DELAY_TIME_MSEC, UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC, 1000);
        accessoriesSlider.setBounds(425, 50, 150, 300);
        delayOfAccessoriesLabel = new JLabel("Delay of accessories: " + convertMsecToSec(accessoriesSlider.getValue()));
        delayOfAccessoriesLabel.setBounds(425, 350, 200, 30);
        panel.add(accessoriesSlider);
        panel.add(delayOfAccessoriesLabel);

        dealersSlider = createSlider(UtilConsts.TimeConsts.MIN_DEALER_DELAY_TIME_MSEC,
                UtilConsts.TimeConsts.MAX_DEALER_DELAY_TIME_MSEC, UtilConsts.TimeConsts.STANDARD_DEALER_DELAY_TIME_MSEC, 1000);
        dealersSlider.setBounds(625, 50, 150, 300);
        delayOfDealersLabel = new JLabel("Delay of dealers: " + convertMsecToSec(accessoriesSlider.getValue()));
        delayOfDealersLabel.setBounds(625, 350, 200, 30);
        panel.add(dealersSlider);
        panel.add(delayOfDealersLabel);

        storageProgressLabel = new JLabel();
        storageProgressLabel.setBounds(50, 500, 300, 80);
        storageProgressLabel.setText("Car storage status: " + factoryProgress.getNumberOfCarsInStorage() + "/" + factoryProgress.getCarStorageLimit());
        storageProgressLabel.setFont(GUISupporter.LABEL_FONT);
        panel.add(storageProgressLabel);

        queueProgressLabel = new JLabel();
        queueProgressLabel.setBounds(350, 500, 300, 80);
        queueProgressLabel.setText("Task queue status: " + factoryProgress.getNumberOfTasksInQueue() + "/" + factoryProgress.getTaskQueueLimit());
        queueProgressLabel.setFont(GUISupporter.LABEL_FONT);
        panel.add(queueProgressLabel);


        createdCarsProgressLabel = new JLabel();
        createdCarsProgressLabel.setBounds(650, 500, 300, 80);
        createdCarsProgressLabel.setText("Created cars: " + factoryProgress.getNumberOfCreatedCars());
        createdCarsProgressLabel.setFont(GUISupporter.LABEL_FONT);
        panel.add(createdCarsProgressLabel);

        frame.add(panel);
        frame.setVisible(false);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        support.firePropertyChange("carcassesDelayTimeMsec", delayOfCarcassesLabel, carcassesSlider.getValue());
        delayOfCarcassesLabel.setText("Delay of carcasses: " + convertMsecToSec(carcassesSlider.getValue()));

        support.firePropertyChange("enginesDelayTimeMsec", delayOfEnginesLabel, enginesSlider.getValue());
        delayOfEnginesLabel.setText("Delay of engines: " + convertMsecToSec(enginesSlider.getValue()));

        support.firePropertyChange("accessoriesDelayTimeMsec", delayOfAccessoriesLabel, accessoriesSlider.getValue());
        delayOfAccessoriesLabel.setText("Delay of accessories: " + convertMsecToSec(accessoriesSlider.getValue()));

        support.firePropertyChange("dealersDelayTimeMsec", delayOfDealersLabel, dealersSlider.getValue());
        delayOfDealersLabel.setText("Delay of dealers: " + convertMsecToSec(dealersSlider.getValue()));
    }

    public void updateProgressLabels() {
        storageProgressLabel.setText("Car storage status: " + factoryProgress.getNumberOfCarsInStorage() + "/" + factoryProgress.getCarStorageLimit());
        queueProgressLabel.setText("Task queue status: " + factoryProgress.getNumberOfTasksInQueue() + "/" + factoryProgress.getTaskQueueLimit());
        createdCarsProgressLabel.setText("Created cars: " + factoryProgress.getNumberOfCreatedCars());
    }

    @Override
    public void startDisplayFrame() {
        frame.setVisible(true);
    }

    @Override
    public void stopDisplayFrame() {
        frame.setVisible(false);
    }

    private JSlider createSlider(int minValue, int maxValue, int currentValue, int majorTickSpace) {
        JSlider slider = new JSlider(SwingConstants.VERTICAL, minValue, maxValue, currentValue);
        slider.setMajorTickSpacing(majorTickSpace);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        return slider;
    }


    private String convertMsecToSec(long durationMsec) {
        return String.format("%.2f sec.", (float) durationMsec / 1000);
    }
}
