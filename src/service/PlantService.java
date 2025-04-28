package service;

/**
 * Класс для описания роста растений на острове
 */

import model.Cell;
import model.Island;
import model.Plant;
import utils.Randomizer;

public class PlantService {

    private final Island island;

    public PlantService(Island island) {
        this.island = island;
    }

    /**
     * Метод роста растений - рандомное количетсво от 4й доли оставшего места в ячейке
     * (Plant.MAX_CAPACITY - (int) cell.countPlants()) - остаток места в ячейке по вместимости растений
     */
    public void growPlant() {
        for (Cell[] x : island.getCells()) {
            for (Cell cell : x) {
                int countNewPlants = Randomizer.getInt((Plant.MAX_CAPACITY - (int) cell.countPlants()) / 4);
                for (int i = 0; i < countNewPlants; i++) {
                    cell.addPlant();
                }
            }
        }
    }
}
