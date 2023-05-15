package ru.nsu.bolotov.view;

import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.progress.CarFactoryProgress;
import ru.nsu.bolotov.util.UtilConsts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.TimeUnit;

public class GUI implements Runnable, PropertyChangeListener {
    private final StartMenu startMenu;
    private final ApplicationView applicationView;
    private String activeFrame;
    private final PropertyChangeSupport support;

    public GUI(CarFactoryProgress factoryProgress) {
        startMenu = new StartMenu();
        startMenu.addPropertyChangeListener(this);
        applicationView = new ApplicationView(factoryProgress);
        applicationView.addPropertyChangeListener(this);
        activeFrame = UtilConsts.GUIConsts.START_MENU;
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
                if (UtilConsts.GUIConsts.START_MENU.equals(activeFrame)) {
                    startMenu.startDisplayFrame();
                } else if (UtilConsts.GUIConsts.APPLICATION_VIEW.equals(activeFrame)) {
                    applicationView.startDisplayFrame();
                    applicationView.updateProgressLabels();
                    support.firePropertyChange(UtilConsts.StringConsts.APPLICATION_WAS_LAUNCH, false, true);
                }
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new BusinessInterruptedException();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case UtilConsts.GUIConsts.ACTIVE_FRAME: {
                this.setActiveFrame((String) event.getNewValue());
                break;
            }
            case UtilConsts.StringConsts.APPLICATION_WAS_CLOSED: {
                support.firePropertyChange(UtilConsts.StringConsts.APPLICATION_WAS_CLOSED, event.getOldValue(), event.getNewValue());
                break;
            }
            case UtilConsts.StringConsts.CHANGE_CARCASSES_DELAY: {
                support.firePropertyChange(UtilConsts.StringConsts.CHANGE_CARCASSES_DELAY, event.getOldValue(), event.getNewValue());
                break;
            }
            case UtilConsts.StringConsts.CHANGE_ENGINES_DELAY: {
                support.firePropertyChange(UtilConsts.StringConsts.CHANGE_ENGINES_DELAY, event.getOldValue(), event.getNewValue());
                break;
            }
            case UtilConsts.StringConsts.CHANGE_ACCESSORIES_DELAY: {
                support.firePropertyChange(UtilConsts.StringConsts.CHANGE_ACCESSORIES_DELAY, event.getOldValue(), event.getNewValue());
                break;
            }
            case UtilConsts.StringConsts.CHANGE_DEALERS_DELAY: {
                support.firePropertyChange(UtilConsts.StringConsts.CHANGE_DEALERS_DELAY, event.getOldValue(), event.getNewValue());
                break;
            }
            default: {
                throw new IllegalArgumentException("Incorrect property field");
            }
        }
    }

    private void setActiveFrame(String frameName) {
        this.activeFrame = frameName;
    }
}
