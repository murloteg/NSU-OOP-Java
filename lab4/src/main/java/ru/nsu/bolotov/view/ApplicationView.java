package ru.nsu.bolotov.view;

import ru.nsu.bolotov.progress.CarFactoryProgress;
import ru.nsu.bolotov.util.UtilConsts;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private final JProgressBar storageProgressBar;
    private final JLabel queueProgressLabel;
    private final JProgressBar queueProgressBar;
    private final JLabel createdCarsLabel;
    private final CarFactoryProgress factoryProgress;
    private final PropertyChangeSupport support;

    public ApplicationView(CarFactoryProgress factoryProgress) {
        this.factoryProgress = factoryProgress;
        support = new PropertyChangeSupport(this);
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Car Factory");
        frame.setBounds(0, 0, 1200, 768);

        JPanel panel = new JPanel();
        panel.setBackground(GUISupporter.STANDART_COLOR);
        panel.setLayout(null);

        JButton exit = new JButton("Exit");
        exit.setBounds(50, 650, UtilConsts.GUIConsts.DEFAULT_BUTTON_WIDTH, UtilConsts.GUIConsts.DEFAULT_BUTTON_HEIGHT);
        exit.addActionListener(event -> {
            support.firePropertyChange(UtilConsts.StringConsts.APPLICATION_WAS_CLOSED, false, true);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        panel.add(exit);

        carcassesSlider = createSlider(UtilConsts.TimeConsts.MIN_SUPPLIER_DELAY_TIME_MSEC,
                UtilConsts.TimeConsts.MAX_SUPPLIER_DELAY_TIME_MSEC, UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC);
        carcassesSlider.setBounds(75, 50, 150, 300);
        delayOfCarcassesLabel = new JLabel("Delay of carcasses: " + convertMsecToSec(carcassesSlider.getValue()));
        delayOfCarcassesLabel.setBounds(65, 350, 200, 30);
        panel.add(carcassesSlider);
        panel.add(delayOfCarcassesLabel);

        enginesSlider = createSlider(UtilConsts.TimeConsts.MIN_SUPPLIER_DELAY_TIME_MSEC,
                UtilConsts.TimeConsts.MAX_SUPPLIER_DELAY_TIME_MSEC, UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC);
        enginesSlider.setBounds(365, 50, 150, 300);
        delayOfEnginesLabel = new JLabel("Delay of engines: " + convertMsecToSec(carcassesSlider.getValue()));
        delayOfEnginesLabel.setBounds(355, 350, 200, 30);
        panel.add(enginesSlider);
        panel.add(delayOfEnginesLabel);

        accessoriesSlider = createSlider(UtilConsts.TimeConsts.MIN_SUPPLIER_DELAY_TIME_MSEC,
                UtilConsts.TimeConsts.MAX_SUPPLIER_DELAY_TIME_MSEC, UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC);
        accessoriesSlider.setBounds(665, 50, 150, 300);
        delayOfAccessoriesLabel = new JLabel("Delay of accessories: " + convertMsecToSec(accessoriesSlider.getValue()));
        delayOfAccessoriesLabel.setBounds(655, 350, 200, 30);
        panel.add(accessoriesSlider);
        panel.add(delayOfAccessoriesLabel);

        dealersSlider = createSlider(UtilConsts.TimeConsts.MIN_DEALER_DELAY_TIME_MSEC,
                UtilConsts.TimeConsts.MAX_DEALER_DELAY_TIME_MSEC, UtilConsts.TimeConsts.STANDARD_DEALER_DELAY_TIME_MSEC);
        dealersSlider.setBounds(965, 50, 150, 300);
        delayOfDealersLabel = new JLabel("Delay of dealers: " + convertMsecToSec(dealersSlider.getValue()));
        delayOfDealersLabel.setBounds(955, 350, 200, 30);
        panel.add(dealersSlider);
        panel.add(delayOfDealersLabel);

        storageProgressLabel = new JLabel();
        storageProgressLabel.setBounds(75, 465, 300, 80);
        storageProgressLabel.setText("Car storage: " + factoryProgress.getNumberOfCarsInStorage() + "/" + factoryProgress.getCarStorageLimit());
        storageProgressLabel.setFont(GUISupporter.LABEL_FONT);
        panel.add(storageProgressLabel);

        storageProgressBar = new JProgressBar(0, factoryProgress.getCarStorageLimit());
        storageProgressBar.setStringPainted(true);
        storageProgressBar.setBounds(70, 505, 290, 80);
        storageProgressBar.setValue(factoryProgress.getNumberOfCarsInStorage());
        panel.add(storageProgressBar);

        queueProgressLabel = new JLabel();
        queueProgressLabel.setBounds(475, 465, 300, 80);
        queueProgressLabel.setText("Task queue: " + factoryProgress.getNumberOfTasksInQueue() + "/" + factoryProgress.getTaskQueueLimit());
        queueProgressLabel.setFont(GUISupporter.LABEL_FONT);
        panel.add(queueProgressLabel);

        queueProgressBar = new JProgressBar(0, factoryProgress.getTaskQueueLimit());
        queueProgressBar.setStringPainted(true);
        queueProgressBar.setBounds(470, 505, 290, 80);
        queueProgressBar.setValue(factoryProgress.getNumberOfTasksInQueue());
        panel.add(queueProgressBar);

        createdCarsLabel = new JLabel();
        createdCarsLabel.setBounds(875, 465, 300, 80);
        createdCarsLabel.setText("Created cars: " + factoryProgress.getNumberOfCreatedCars());
        createdCarsLabel.setFont(GUISupporter.LABEL_FONT);
        panel.add(createdCarsLabel);

        frame.add(panel);
        frame.setVisible(false);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        support.firePropertyChange(UtilConsts.StringConsts.CHANGE_CARCASSES_DELAY, delayOfCarcassesLabel, carcassesSlider.getValue());
        delayOfCarcassesLabel.setText("Delay of carcasses: " + convertMsecToSec(carcassesSlider.getValue()));

        support.firePropertyChange(UtilConsts.StringConsts.CHANGE_ENGINES_DELAY, delayOfEnginesLabel, enginesSlider.getValue());
        delayOfEnginesLabel.setText("Delay of engines: " + convertMsecToSec(enginesSlider.getValue()));

        support.firePropertyChange(UtilConsts.StringConsts.CHANGE_ACCESSORIES_DELAY, delayOfAccessoriesLabel, accessoriesSlider.getValue());
        delayOfAccessoriesLabel.setText("Delay of accessories: " + convertMsecToSec(accessoriesSlider.getValue()));

        support.firePropertyChange(UtilConsts.StringConsts.CHANGE_DEALERS_DELAY, delayOfDealersLabel, dealersSlider.getValue());
        delayOfDealersLabel.setText("Delay of dealers: " + convertMsecToSec(dealersSlider.getValue()));
    }

    public void updateProgressLabels() {
        storageProgressLabel.setText("Car storage: " + factoryProgress.getNumberOfCarsInStorage() + "/" + factoryProgress.getCarStorageLimit());
        storageProgressBar.setValue(factoryProgress.getNumberOfCarsInStorage());
        queueProgressLabel.setText("Task queue: " + factoryProgress.getNumberOfTasksInQueue() + "/" + factoryProgress.getTaskQueueLimit());
        queueProgressBar.setValue(factoryProgress.getNumberOfTasksInQueue());
        createdCarsLabel.setText("Created cars: " + factoryProgress.getNumberOfCreatedCars());
    }

    @Override
    public void startDisplayFrame() {
        frame.setVisible(true);
    }

    @Override
    public void stopDisplayFrame() {
        frame.setVisible(false);
    }

    private JSlider createSlider(int minValue, int maxValue, int currentValue) {
        JSlider slider = new JSlider(SwingConstants.VERTICAL, minValue, maxValue, currentValue);
        slider.setMajorTickSpacing(1000);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        return slider;
    }

    private String convertMsecToSec(long durationMsec) {
        return String.format("%.2f sec.", (float) durationMsec / 1000);
    }
}
