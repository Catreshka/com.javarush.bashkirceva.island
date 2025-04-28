package model.animal;

/**
 * Класс травоядных
 */

import lombok.Getter;
import model.animal.herbivores.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public abstract class Herbivore extends Animal {

    private static List<Class<? extends Animal>> arrayChildren = Arrays.asList(
            Caterpillar.class,
            Deer.class,
            Duck.class,
            Goat.class,
            Hare.class,
            Horse.class,
            Mouse.class,
            Sheep.class
    );

    public Herbivore(double weight, int maxCapacity, int speedMove, double foodAmount, Map<Class<?>,Integer> eatProbability) {
        super(weight, maxCapacity, speedMove, foodAmount, eatProbability);
    }

    /**
     * Метод для получения наследников класса Herbivore
     */
    public static List<Class<? extends Animal>> getChildrenHerbivore() {
        return arrayChildren;
    }
}
