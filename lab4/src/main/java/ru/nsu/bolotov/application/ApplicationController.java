package ru.nsu.bolotov.application;

import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.parser.ConfigFileParser;

import ru.nsu.bolotov.progress.CarFactoryProgress;
import ru.nsu.bolotov.storages.CarStorage;
import ru.nsu.bolotov.storages.ComponentStorage;
import ru.nsu.bolotov.threadpool.actors.Dealer;
import ru.nsu.bolotov.threadpool.actors.Supplier;
import ru.nsu.bolotov.threadpool.actors.Worker;
import ru.nsu.bolotov.threadpool.controller.StorageController;
import ru.nsu.bolotov.threadpool.tasks.SupplierOrder;
import ru.nsu.bolotov.threadpool.tasks.TaskQueue;
import ru.nsu.bolotov.util.UtilConsts;
import ru.nsu.bolotov.view.GUI;
import ru.nsu.bolotov.view.UserWaiter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ApplicationController implements PropertyChangeListener {
    private final List<Dealer> dealers = new LinkedList<>();
    private final List<Supplier> carcassesSuppliers = new LinkedList<>();
    private final List<Supplier>  enginesSuppliers = new LinkedList<>();
    private final List<Supplier> accessoriesSuppliers = new LinkedList<>();
    private int carcassesDelayTimeMsec = UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC;
    private int enginesDelayTimeMsec = UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC;
    private int accessoriesDelayTimeMsec = UtilConsts.TimeConsts.STANDARD_SUPPLIER_DELAY_TIME_MSEC;
    private int dealersDelayTimeMsec = UtilConsts.TimeConsts.STANDARD_DEALER_DELAY_TIME_MSEC;
    private final UserWaiter userWaiter = new UserWaiter();

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
        waitLaunchOfApplication(userWaiter);

        ComponentStorage<Component> carcasses = new ComponentStorage<>(UtilConsts.ComponentsConsts.REQUIRED_CARCASSES_NUMBER, maxCarcassNumber);
        ComponentStorage<Component> engines = new ComponentStorage<>(UtilConsts.ComponentsConsts.REQUIRED_ENGINES_NUMBER, maxEnginesNumber);
        int accessoriesRequiredNumber = UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER + UtilConsts.ComponentsConsts.REQUIRED_DOORS_NUMBER;
        ComponentStorage<Component> accessories = new ComponentStorage<>(accessoriesRequiredNumber, maxAccessoriesNumber);

        SupplierOrder orderOfCarcasses = new SupplierOrder(new String[] {"CARCASS"}, new int[] {UtilConsts.ComponentsConsts.REQUIRED_CARCASSES_NUMBER});
        carcassesSuppliers.add(new Supplier(carcasses, carcassesDelayTimeMsec, orderOfCarcasses));

        SupplierOrder orderOfEngines = new SupplierOrder(new String[] {"ENGINE"}, new int[] {UtilConsts.ComponentsConsts.REQUIRED_ENGINES_NUMBER});
        enginesSuppliers.add(new Supplier(engines, enginesDelayTimeMsec, orderOfEngines));

        prepareAccessoriesSuppliers(accessoriesSuppliersNumber, accessories);
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
            case "isApplicationLaunched": {
                userWaiter.setStatusOfLaunch((boolean) event.getNewValue());
                break;
            }
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

    private void setCarcassesDelayTime(int delayTimeMsec) {
        this.carcassesDelayTimeMsec = delayTimeMsec;
    }

    private void setEnginesDelayTime(int delayTimeMsec) {
        this.enginesDelayTimeMsec = delayTimeMsec;
    }

    private void setAccessoriesDelayTime(int delayTimeMsec) {
        this.accessoriesDelayTimeMsec = delayTimeMsec;
    }

    private void setDealersDelayTime(int delayTimeMsec) {
        this.dealersDelayTimeMsec = delayTimeMsec;
    }

    private void waitLaunchOfApplication(UserWaiter userWaiter) {
        Thread waitingThread = new Thread(userWaiter);
        waitingThread.start();
        while (!userWaiter.getStatusOfLaunch()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new BusinessInterruptedException();
            }
        }
    }

    private void prepareAccessoriesSuppliers(int accessoriesSuppliersNumber, ComponentStorage<Component> accessories) {
        String[] components = new String[] {"WHEEL", "DOOR"};
        int[] requiredNumbersOfComponents = new int[] {UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER, UtilConsts.ComponentsConsts.REQUIRED_DOORS_NUMBER};
        SupplierOrder orderOfAccessories = new SupplierOrder(components, requiredNumbersOfComponents);
        for (int i = 0; i < accessoriesSuppliersNumber; ++i) {
            accessoriesSuppliers.add(new Supplier(accessories, accessoriesDelayTimeMsec, orderOfAccessories));
        }
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
}
