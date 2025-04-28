package model;

/**
 * Класс для создания острова
 */

import lombok.Getter;
import model.animal.Animal;
import utils.SimulationSettings;

@Getter
public class Island {
    private final int lengthIsland;
    private final int widthIsland;
    private final Cell[][] cells;

    public Island() {
        this.widthIsland = SimulationSettings.ISLAND_WIDTH;
        this.lengthIsland = SimulationSettings.ISLAND_LENGTH;
        this.cells = new Cell[widthIsland][lengthIsland];
        createCells();
    }

    /**
     * Метод для распределения ячеек по координатам острова
     */
    public void createCells() {
        for (int row = 0; row < widthIsland; row++) {
            for (int column = 0; column < lengthIsland; column++) {
                cells[row][column] = new Cell(row,column);
            }
        }
    }

    /**
     * Метод для печати острова
     */
    public void printIsland() {
        System.out.println("-------------------------------------------------------------------------------------");
        for (int row = 0; row < widthIsland; row++) {
            for (int column = 0; column < lengthIsland; column++) {
                System.out.print(cells[row][column].toString());
                if (column != lengthIsland - 1) {
                    System.out.print("  ---  ");
                }
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

    /**
     * Метод для получения ячейки по координатам
     */
    public Cell getCell(int x, int y) {
        if (x >= 0 && x < widthIsland && y >= 0 && y < lengthIsland) {
            return cells[x][y];
        }
        return null;
    }

    /**
     * Метод получения всех ячеек
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Метод поиска ячейки, в которой находится животное
     */
    public Cell findCellForAnimal(Animal animal) {
        for (int row = 0; row < widthIsland; row++) {
            for (int column = 0; column < lengthIsland; column++) {
                if (cells[row][column].checkExistAnimalInCell(animal) != null) {
                    return cells[row][column];
                }
            }
        }
        return null;
    }
}
