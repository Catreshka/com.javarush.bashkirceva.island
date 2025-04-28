package model.animal;

/**
 * Абстрактный класс Animal для всех животных
 */

import lombok.Getter;
import model.Cell;
import model.Island;
import model.Plant;
import statistics.Statistic;
import utils.Logs;
import utils.Randomizer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public abstract class Animal {

    private static final int MAX_ENERGY = 3;

    private double weight;
    private int maxCapacity;
    private int speedMove;
    private double foodAmount;
    private Map<Class<?>,Integer> eatProbability;

    protected boolean isAlive;
    protected int lifeEnergy;
    protected Cell cell;

    public Animal(double weight, int maxCapacity, int speedMove, double foodAmount, Map<Class<?>,Integer> eatProbability) {
        this.isAlive = true;
        this.lifeEnergy = MAX_ENERGY;
        this.weight = weight;
        this.maxCapacity = maxCapacity;
        this.speedMove = speedMove;
        this.foodAmount = foodAmount;
        this.eatProbability = eatProbability;
    }

    /**
     * Метод еды для всех животных
     * проверяю есть ли энергия - если нет животное умирает
     * проверяю если энергия на максимуме - ничего не ем
     * проверяю - среди возможной еды только растения?
        * да: ем растение
        * нет:
            * получаю лист потенциальной еды доступной в ячейке
            * рандомно выбираю класс из списка жертв для животного
            * получаю вероятность для этого класса (из карты еды)
            * с учетом этой вероятности ем либо растение либо животное (в зависимости от выбранного класса)
     */
    public void eat(Cell cell) {
       checkRemainingActivity(cell);
        if (lifeEnergy != MAX_ENERGY) {
            if (checkAnimalEatJustPlant()) {
                addEnergyAndEatPlant(cell);
            } else {
                List<Class> foodList = getPotentialFoodClassList(cell);
                if (foodList != null) {
                    Class food = Randomizer.randomItem(foodList);
                    int chance = eatProbability.get(food);
                    if (Randomizer.getProbability(chance)) {
                        if (food == Plant.class) {
                            addEnergyAndEatPlant(cell);
                        } else {
                            List<Animal> preyList = cell.getListAnimalByClassInCell(food);
                            Animal prey = Randomizer.randomItem(preyList);
                            addEnergyAndEatAnimal(prey,cell);
                        }
                    }
                }
            }
        }
    }

    /**
     * Метод возвращает пересесечние списка классов, которые есть в ячейке и тех, что может есть животное
     * получаю лист классов от животного
     * получаю лист классов из ячейки
     * если в ячейке есть классы, которые можно съесть, формирую список
     */
    protected List<Class> getPotentialFoodClassList(Cell cell) {
        List<Class> foodFromAnimal = eatProbability
                .keySet()
                .stream()
                .collect(Collectors.toList());
        List<Class> foodFromCell = cell.getAllClassesInCell();
        List<Class> potentialFood = foodFromCell
                .stream()
                .filter(foodFromAnimal::contains)
                .collect(Collectors.toList());
        return potentialFood;
    }

    /**
     * Метод проверяет карту еды животного - животное есть только растения или нет
     */
    protected boolean checkAnimalEatJustPlant() {
        Optional<?> classWithProbability100 =
                eatProbability
                        .entrySet()
                        .stream()
                        .filter(e -> e.getValue() == 100)
                        .map(Map.Entry::getKey)
                        .findFirst();
        return classWithProbability100.isPresent();
    }

    /**
     * Метод добавляет энергию и удаляет съеденные растения из ячейки
     * еды больше чем нужно до полного насыщения?
        * да:
            * увеличиваю энергию до максимума
            * удаляю съеденные растения
            * списываю энергию
        * нет:
            * увеличиваю количество энергии пропорционально съеденной еде
            * удаляю съеденные растения
            * списываю энергию
     */
    protected void addEnergyAndEatPlant(Cell cell) {
        int amountFoodForEat = amountFoodForEat();
        int countPlants = cell.countPlants();
        if (amountFoodForEat <= countPlants) {
            lifeEnergy = MAX_ENERGY;
            for (int i = 0; i < amountFoodForEat; i++) {
                cell.removePlant();
            }
        } else {
            lifeEnergy = MAX_ENERGY - Math.round((amountFoodForEat-countPlants) / amountFoodForOneEnergy());
            for (int i = 0; i < countPlants; i++) {
                cell.removePlant();
            }
        }
        lifeEnergy--;
    }

    /**
     * Метод добавляет энергию и убивает съеденное животное
     * вес жертвы больше чем нужно до полного насыщения?
        * да:
            * увеличиваю энергию до максимума
        * нет:
            * увеличиваю количество энергии пропорционально съеденной еде
     * списываю энергию
     * убиваю жертву
     * удаляю жертву из ячейки
     */
    protected void addEnergyAndEatAnimal(Animal prey, Cell cell) {
        int amountFoodForEat = amountFoodForEat();
        if (amountFoodForEat <= prey.weight) {
            lifeEnergy = MAX_ENERGY;
        } else {
            lifeEnergy = MAX_ENERGY - Math.round((amountFoodForEat - (int) prey.weight) / amountFoodForOneEnergy());
        }
        lifeEnergy--;
        Statistic.addAnimalInDeathsByPredationStatistic(prey);
        prey.die(cell);
    }

    /**
     * Метод возвращает количество еды, которое необходимо животному до полного насыщения
     * amountFoodForOneEnergy = количество еды, которое нужно животному для восполнения 1 единицы энергии
     * MAX_ENERGY-lifeEnergy = количество энергии до полного насыщения
     */
    protected int amountFoodForEat() {
        return amountFoodForOneEnergy() * (MAX_ENERGY - lifeEnergy);
    }

    /**
     * Метод возвращает количество еды, которое нужно животному для восполнения 1 единицы энергии (для простоты округляю)
     * FOOD_AMOUNT/MAX_ENERGY = количество кг на насыщениее 1 единицы энергии
     */
    protected int amountFoodForOneEnergy() {
        return (int) Math.round(foodAmount / MAX_ENERGY);
    }

    /**
     * Метод, который проверяет осталась ли энергия
     */
    protected boolean checkRemainingActivity(Cell cell) {
        if (lifeEnergy == 0) {
            die(cell);
            return false;
        }
        return true;
    }

    /**
     * Метод убивает животное и удаляет его из ячейки
     */
    protected void die(Cell cell) {
        if (isAlive) {
            isAlive=false;
            if (cell != null) {
                Statistic.addAnimalInDeathsByEnergyStatistic(this);
                cell.removeAnimal(this);
            }
        }
    }

    /**
     * Метод размножения для всех животных
     * проверяю есть ли энергия - если нет умираю
     * проверяю есть ли животное моего класса
     * проверяю не максимальное ли количество животных этого класса в ячейке
     * создаю животное
     * списываю энергию
     */
    public void reproduce(Cell cell) {
        checkRemainingActivity(cell);
        if (cell.getListAnimalByClassInCell(getClass()) != null) {
            if (cell.getCountAnimalsByClassInCell(getClass()) < maxCapacity) {
                try {
                    Animal animal = getClass().newInstance();
                    Statistic.addAnimalInBirthStatistic(animal);
                    cell.addAnimal(animal);
                }
                catch (InstantiationException |  IllegalAccessException e) {
                    Logs.logError("Ошибка при создании животного", e);
                }
            }
            lifeEnergy--;
        }
    }

    /**
     * Метод передвижения для всех животных
     * проверяю есть ли энергия - если нет умираю
     * выбираю рандомное направление
     * выбираю рандом количество шагов с учетом максимального шага для животного
     * проверяю - есть ли там клетка
     * проверяю - есть ли лимит по животным этого типа
        * нет: перемещаю
        * да: проверяю есть ли соседняя клетка
            * да: проверяю есть ли лимит по животным этого типа
                * нет: перемещаю
                * да: остаюсь на месте
     * списываю -1 энергию
     */
    public void move(Island island) {
        Cell startCell = island.findCellForAnimal(this);
        checkRemainingActivity(startCell);
        if (!isAlive || startCell == null) {
            return;
        }
        String direction = Randomizer.randomDirection();
        int maxStep = speedMove;
        int step = Randomizer.getInt(maxStep);
        if (step != 0) {
            int x = startCell.getX();
            int y = startCell.getY();
            int newX;
            int newY;
            int newMaxStepX;
            int newMaxStepY;
            switch (direction) {
                case "LEFT": newX = x - 1; newY = y; newMaxStepX = x - step; newMaxStepY = y; break;
                case "RIGHT": newX = x + 1; newY = y; newMaxStepX = x + step; newMaxStepY = y; break;
                case "TOP": newX = x; newY = y + 1; newMaxStepX = x; newMaxStepY = y + step; break;
                case "BOTTOM": newX = x; newY = y - 1; newMaxStepX = x; newMaxStepY = y - step; break;
                default: newX = x; newY = y; newMaxStepX = x; newMaxStepY = y;
            }
            if (island.getCell(newMaxStepX,newMaxStepY) != null) {
                if (island.getCell(newMaxStepX,newMaxStepY).getCountAnimalsByClassInCell(getClass()) < maxCapacity) {
                    island.getCell(newMaxStepX,newMaxStepY).addAnimal(this);
                    startCell.removeAnimal(this);
                }
                lifeEnergy--;
            } else if (island.getCell(newX,newY) != null) {
                if (island.getCell(newX,newY).getCountAnimalsByClassInCell(getClass()) < maxCapacity) {
                    island.getCell(newX,newY).addAnimal(this);
                    startCell.removeAnimal(this);
                }
                lifeEnergy--;
            }
        }
    }

    /**
     * Абстрактный метод получения emoji животного
     */
    public abstract String getEmoji();
}
