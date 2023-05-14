package ru.nsu.bolotov.application;

import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.parser.ConfigFileParser;

import ru.nsu.bolotov.progress.CarFactoryProgress;
import ru.nsu.bolotov.storages.CarStorage;
import ru.nsu.bolotov.storages.ComponentStorage;
import ru.nsu.bolotov.threadpool.actors.Dealer;
import ru.nsu.bolotov.threadpool.actors.Supplier;
import ru.nsu.bolotov.threadpool.actors.Worker;
import ru.nsu.bolotov.threadpool.controller.StorageController;
import ru.nsu.bolotov.threadpool.tasks.TaskQueue;
import ru.nsu.bolotov.util.UtilConsts;
import ru.nsu.bolotov.view.GUI;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

public class ApplicationController implements PropertyChangeListener {
    private List<Dealer> dealers;
    private List<Supplier> carcassesSuppliers;
    private List<Supplier>  enginesSuppliers;
    private List<Supplier> accessoriesSuppliers;
    private int carcassesDelayTimeMsec = UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC;
    private int enginesDelayTimeMsec = UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC;
    private int accessoriesDelayTimeMsec = UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC;
    private int dealersDelayTimeMsec = UtilConsts.TimeConsts.STANDARD_DEALER_DELAY_TIME_MSEC;

    public void start() {
        int maxCarcassNumber = ConfigFileParser.getMaxCarcassNumber();
        int maxEnginesNumber = ConfigFileParser.getMaxEnginesNumber();
        int maxAccessoriesNumber = ConfigFileParser.getMaxAccessoriesNumber();
        int maxCarNumber = ConfigFileParser.getMaxCarNumber();
        int accessoriesSuppliersNumber = ConfigFileParser.getAccessoriesSuppliersNumber();
        int workersNumber = ConfigFileParser.getWorkersNumber();
        int dealersNumber = ConfigFileParser.getDealersNumber();
        int maxTasksInQueue = ConfigFileParser.getMaxTasksInQueue();
        boolean loggingStatus = ConfigFileParser.getLoggingStatus();

        CarStorage carStorage = new CarStorage(maxCarNumber);
        TaskQueue taskQueue = new TaskQueue(maxTasksInQueue);

        CarFactoryProgress factoryProgress = new CarFactoryProgress(taskQueue, carStorage);
        GUI view = new GUI(factoryProgress);
        view.addPropertyChangeListener(this);
        Thread viewThread = new Thread(view);
        viewThread.start();

        ComponentStorage<Component> carcasses = new ComponentStorage<>(UtilConsts.ComponentsConsts.REQUIRED_CARCASSES_NUMBER, maxCarcassNumber);
        ComponentStorage<Component> engines = new ComponentStorage<>(UtilConsts.ComponentsConsts.REQUIRED_ENGINES_NUMBER, maxEnginesNumber);
        int accessoriesRequiredNumber = UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER + UtilConsts.ComponentsConsts.REQUIRED_DOORS_NUMBER;
        ComponentStorage<Component> accessories = new ComponentStorage<>(accessoriesRequiredNumber, maxAccessoriesNumber);

        carcassesSuppliers = new LinkedList<>();
        carcassesSuppliers.add(new Supplier(carcasses, carcassesDelayTimeMsec, new String[] {"CARCASS"}));

        enginesSuppliers = new LinkedList<>();
        enginesSuppliers.add(new Supplier(engines, enginesDelayTimeMsec, new String[] {"ENGINE"}));

        accessoriesSuppliers = new LinkedList<>();
        distributeAccessoriesSuppliers(accessoriesSuppliersNumber, accessories);
        List<Thread> threadsOfSuppliers = new LinkedList<>();
        for (Supplier supplier : carcassesSuppliers) {
            threadsOfSuppliers.add(new Thread(supplier));
        }
        for (Supplier supplier : enginesSuppliers) {
            threadsOfSuppliers.add(new Thread(supplier));
        }
        for (Supplier supplier : accessoriesSuppliers) {
            threadsOfSuppliers.add(new Thread(supplier));
        }

        dealers = new LinkedList<>();
        for (int i = 0; i < dealersNumber; ++i) {
            dealers.add(new Dealer(carStorage, dealersDelayTimeMsec, loggingStatus));
        }
        List<Thread> threadsOfDealers = new LinkedList<>();
        for (Dealer dealer : dealers) {
            threadsOfDealers.add(new Thread(dealer));
        }

        List<Worker> workers = new LinkedList<>();
        for (int i = 0; i < workersNumber; ++i) {
            workers.add(new Worker(taskQueue));
        }
        List<Thread> threadsOfWorkers = new LinkedList<>();
        for (Worker worker : workers) {
            threadsOfWorkers.add(new Thread(worker));
        }

        StorageController storageController = new StorageController(taskQueue, carcasses, engines, accessories, carStorage, loggingStatus);
        carStorage.addPropertyChangeListener(storageController);
        Thread controllerThread = new Thread(storageController);
        controllerThread.start();
        launchAllThreads(threadsOfSuppliers, threadsOfDealers, threadsOfWorkers);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case "carcassesDelayTimeMsec": {
                this.setCarcassesDelayTime((Integer) event.getNewValue());
                changeDelayTimeOfSuppliers(carcassesSuppliers, carcassesDelayTimeMsec);
                break;
            }
            case "enginesDelayTimeMsec": {
                this.setEnginesDelayTime((Integer) event.getNewValue());
                changeDelayTimeOfSuppliers(enginesSuppliers, enginesDelayTimeMsec);
                break;
            }
            case "accessoriesDelayTimeMsec": {
                this.setAccessoriesDelayTime((Integer) event.getNewValue());
                changeDelayTimeOfSuppliers(accessoriesSuppliers, accessoriesDelayTimeMsec);
                break;
            }
            case "dealersDelayTimeMsec": {
                this.setDealersDelayTime((Integer) event.getNewValue());
                changeDelayTimeOfDealers(dealers, dealersDelayTimeMsec);
                break;
            }
            default: {
                throw new IllegalArgumentException("Incorrect property field");
            }
        }
    }

    public void setCarcassesDelayTime(int delayTimeMsec) {
        this.carcassesDelayTimeMsec = delayTimeMsec;
    }

    public void setEnginesDelayTime(int delayTimeMsec) {
        this.enginesDelayTimeMsec = delayTimeMsec;
    }

    public void setAccessoriesDelayTime(int delayTimeMsec) {
        this.accessoriesDelayTimeMsec = delayTimeMsec;
    }

    public void setDealersDelayTime(int delayTimeMsec) {
        this.dealersDelayTimeMsec = delayTimeMsec;
    }

    private void launchAllThreads(List<Thread> threadsOfSuppliers, List<Thread> threadsOfDealers, List<Thread> threadsOfWorkers) {
        for (Thread thread : threadsOfSuppliers) {
            thread.start();
        }
        for (Thread thread : threadsOfDealers) {
            thread.start();
        }
        for (Thread thread : threadsOfWorkers) {
            thread.start();
        }
    }

    private void changeDelayTimeOfDealers(List<Dealer> dealers, int delayTimeMsec) {
        for (Dealer dealer : dealers) {
            dealer.setDelayTime(delayTimeMsec);
        }
    }

    private void changeDelayTimeOfSuppliers(List<Supplier> suppliers, int delayTimeMsec) {
        for (Supplier supplier : suppliers) {
            supplier.setDelayTime(delayTimeMsec);
        }
    }

    private void distributeAccessoriesSuppliers(int accessoriesSuppliersNumber, ComponentStorage<Component> accessories) {
        for (int i = 0; i < accessoriesSuppliersNumber; ++i) {
            accessoriesSuppliers.add(new Supplier(accessories, accessoriesDelayTimeMsec, new String[] {"WHEEL", "DOOR"}));
        }
    }
}
