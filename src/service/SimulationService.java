package service;

/**
 * Класс для управления симуляцией острова
 */

import model.Island;
import statistics.Statistic;
import utils.SimulationSettings;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationService {

    private final Island island;
    private final AnimalService animalService;
    private final PlantService plantService;

    private final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(3);

    private volatile boolean isRunning = false;
    private final AtomicInteger dayCounter = new AtomicInteger(0);

    public SimulationService(Island island) {
        this.island = island;
        this.animalService = new AnimalService();
        this.plantService = new PlantService(island);
    }

    /**
     * Метод запуска симуляции
     */
    public void startSimulation() {
        if (isRunning) return;
        isRunning = true;
        schedule.scheduleAtFixedRate(
                this::oneDay,
                0,
                SimulationSettings.DAY_DURATION_MS,
                TimeUnit.MILLISECONDS
        );
    }

    /**
     * Метод одного дня
     */
    public void oneDay() {
        int currentDay = dayCounter.incrementAndGet();
        System.out.println("День " + currentDay);
        plantService.growPlant();
        animalService.startLifeCycle(island);
        if (currentDay % SimulationSettings.STATISTICS_INTERVAL_DAYS == 0) {
            Statistic.printStatistics(island);
        }
        island.printIsland();
    }

    /**
     * Остановка симуляции
     */
    public void stopSimulation() {
        isRunning = false;
        animalService.stopLifeCycle();
        schedule.shutdownNow();
        System.out.println("Всего дней: " + dayCounter.get());
    }
}
