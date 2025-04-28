package service;

/**
 * Класс для описания жизненного цикла животного на острове
 */

import model.Cell;
import model.Island;
import model.animal.Animal;
import utils.Logs;
import utils.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnimalService {

    private final ExecutorService animalExecutor = Executors.newFixedThreadPool(8);;

    /**
     * Жизненный цикл животного
        * поесть
        * размножиться
        * переместиться
     */
    public void startLifeCycle(Island island) {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (Cell[] x : island.getCells()) {
            for (Cell cell : x) {
                for (Animal animal : cell.getAnimals()) {
                    tasks.add(() -> {
                            if (animal.isAlive()) {
                                animal.eat(cell);
                                if (Randomizer.getProbability(50)) {
                                    animal.reproduce(cell);
                                }
                                animal.move(island);
                            }
                            return null;
                    });
                }
            }
        }
        try {
            animalExecutor.invokeAll(tasks);
        } catch (InterruptedException e) {
            Logs.logError("Поток жизненного цикла животного был прерван",e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Остановка цикла
     */
    public void stopLifeCycle() {
        animalExecutor.shutdownNow();
    }
}
