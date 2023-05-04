package ru.nsu.bolotov.application;

import org.slf4j.LoggerFactory;
import ru.nsu.bolotov.components.accessories.Accessories;
import ru.nsu.bolotov.components.carcass.Carcass;
import ru.nsu.bolotov.components.engine.Engine;
import ru.nsu.bolotov.parser.ConfigFileParser;

import org.slf4j.Logger;
import ru.nsu.bolotov.storages.CarStorage;
import ru.nsu.bolotov.storages.ComponentStorage;
import ru.nsu.bolotov.threadpool.actors.dealers.Dealer;
import ru.nsu.bolotov.threadpool.actors.workers.Worker;

import java.util.List;

public class ApplicationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);
    private ComponentStorage<Carcass> carcasses;
    private ComponentStorage<Engine> engines;
    private ComponentStorage<Accessories> accessories;
    private CarStorage cars;
    private List<Worker> workers;
    private List<Dealer> dealers;
    private int suppliersDelayTimeMsec = 500; // FIXME
    private int dealersDelayTimeMsec = 1000; // FIXME

    public void start() {
        int maxCarcassNumber = ConfigFileParser.getMaxCarcassNumber();
        int maxEnginesNumber = ConfigFileParser.getMaxEnginesNumber();
        int maxAccessoriesNumber = ConfigFileParser.getMaxAccessoriesNumber();
        int accessoriesSuppliersNumber = ConfigFileParser.getAccessoriesSuppliersNumber();
        int workersNumber = ConfigFileParser.getWorkersNumber();
        int dealersNumber = ConfigFileParser.getDealersNumber();
        boolean loggingStatus = ConfigFileParser.getLoggingStatus();

    }
}
