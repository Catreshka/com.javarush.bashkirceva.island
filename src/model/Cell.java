package model;

/**
 * Класс для создания ячейки карты
 */

import factory.AnimalFactory;
import lombok.Getter;
import model.animal.Animal;
import model.animal.Herbivore;
import model.animal.Predator;
import statistics.Statistic;
import utils.Logs;
import utils.Randomizer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Getter
public class Cell {
    int count;
    int x;
    int y;

    private final List<Animal> animals = new CopyOnWriteArrayList<>();
    private final List<Plant> plants = new CopyOnWriteArrayList<>();

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        initializePlant();
        initializeAnimal();
    }

    /**
     * Метод инициализации растений в ячейке при ее создании (с вероятностью 50% рандомное число)
     */
    public void initializePlant() {
        if (Randomizer.getProbability(50)) {
            int amount = Randomizer.getInt(Plant.MAX_CAPACITY);
            for (int i = 0; i < amount; i++) {
                plants.add(new Plant());
            }
        }
    }

    /**
     * Метод инициализации хищников и травоядных в ячейке при ее создании с долей вероятности:
     * для хищников 30
     * для травоядных 50
     */
    public void initializeAnimal() {
        Class<? extends Animal> randomPredatorClass = Randomizer.randomClass(Predator.getChildrenPredator());
        Class<? extends Animal> randomHerbivoreClass = Randomizer.randomClass(Herbivore.getChildrenHerbivore());
        if (Randomizer.getProbability(30)) {
            initializeAnimalsByClass(randomPredatorClass);
        }
        if (Randomizer.getProbability(50)) {
            initializeAnimalsByClass(randomHerbivoreClass);
        }
    }

    /**
     * Метод инициализации животного нужного класса в ячейке
     */
    public void initializeAnimalsByClass(Class<? extends Animal> animalClass) {
        AnimalFactory animalFactory = new AnimalFactory();
        int maxClassCapacity = 0;
        try {
            Field field = animalClass.getDeclaredField("MAX_CAPACITY");
            field.setAccessible(true);
            maxClassCapacity =  (int) field.get(animalClass);
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            Logs.logError("Не удалось получить поле MAX_CAPACITY у класса" + animalClass, e);
        }
        int amountAnimalForInitialize = Randomizer.getInt(maxClassCapacity/5);
        if (amountAnimalForInitialize > 0) {
            for (int i = 0; i < amountAnimalForInitialize; i++) {
                Animal newAnimal = animalFactory.createAnimal(animalClass);
                animals.add(newAnimal);
                Statistic.addAnimalInBirthStatistic(newAnimal);
            }
        }
    }

    /**
     * Метод проверяет существование конкретного животного в ячейке
     */
    public Cell checkExistAnimalInCell(Animal animal) {
        if (animals.contains(animal)) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * Метод возвращает список всех животных указанного класса
     */
    public <T extends Animal> List<T> getListAnimalByClassInCell(Class<T> requiredClass) {
        List<T> animalList =
                animals.stream()
                        .filter(requiredClass::isInstance)
                        .map(requiredClass::cast)
                        .filter(Animal::isAlive)
                        .collect(Collectors.toList());
        return animalList;
    }

    /**
     * Метод считает количество животных определенного класса в ячейке
     */
    public long getCountAnimalsByClassInCell(Class<? extends Animal> animalClass) {
        return animals
                .stream()
                .filter(e -> e.isAlive() == true)
                .filter(e -> e.getClass() == animalClass)
                .count();
    }

    /**
     * Метод считает количество животных каждого класса в ячейке
     */
    public Map<Class<? extends Animal>, Long> countAnimals() {
        Map<Class<? extends Animal>, Long> animalCounts = animals
                .stream()
                .filter(e -> e.isAlive() == true)
                .collect(Collectors.groupingBy(
                        animal -> animal.getClass(),
                        Collectors.counting()
                ));
        return animalCounts;
    }

    /**
     * Проверяем максимальное количество животных класса в ячейке и если можно, добавляем животное
     */
    public void addAnimal(Animal animal) {
        int maxCapacity = animal.getMaxCapacity();
        if (getCountAnimalsByClassInCell(animal.getClass()) < maxCapacity) {
            animals.add(animal);
        }
    }

    /**
     * Проверяем максимальное количество растений в ячейке и если можно, добавляем
     */
    public void addPlant() {
        int maxCapacity = Plant.MAX_CAPACITY;
        if (plants.size() < maxCapacity) {
            plants.add(new Plant());
        }
    }

    /**
     * Метод считает количество растений в ячейке
     */
    public int countPlants() {
        return plants.size();
    }

    /**
     * Удаление животного из локации
     */
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    /**
     * Удаление растения из локации (удаляю с начала списка)
     */
    public void removePlant() {
        plants.remove(0); // ПОЧЕМУ ТО УДАЛЯЕТ 2
    }

    /**
     * Метод возвращает список всех классов в ячейке
     */
    public List<Class> getAllClassesInCell() {
        List<Class> uniqueClasses =
                animals.stream()
                        .filter(Animal::isAlive)
                        .map(Object::getClass)
                        .distinct()
                        .collect(Collectors.toList());
        if (countPlants() != 0) {
            uniqueClasses.add(Plant.class);
        }
        return uniqueClasses;
    }

    @Override
    public String toString() {
        String str = "";
        for (Map.Entry<Class<? extends Animal>, Long> entry : countAnimals().entrySet()) {
            Class<? extends Animal> animalClass = entry.getKey();
            Long count = entry.getValue();
            try {
                str = str + count + " " + animalClass.getDeclaredConstructor().newInstance().getEmoji() + " ";
            } catch (Exception e) {
                Logs.logError("Произошла ошибка при получении эмоджи животного", e);
            }
        }
        try {
            str = str + "(" + countPlants() + Plant.class.getDeclaredConstructor().newInstance().getEmoji() + ")";
        } catch (Exception e) {
            Logs.logError("Произошла ошибка при получении эмоджи растения", e);
        }
        return str;
    }
}
