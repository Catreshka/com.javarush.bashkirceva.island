package model.animal;

/**
 * Класс хищников
 */

import lombok.Getter;
import model.animal.predators.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public abstract class Predator extends Animal {

    private static List<Class<? extends Animal>> arrayChildren = Arrays.asList(
            Bear.class,
            Earl.class,
            Fox.class,
            Snake.class,
            Wolf.class
    );

    public Predator(double weight, int maxCapacity, int speedMove, double foodAmount, Map<Class<?>,Integer> eatProbability) {
        super(weight, maxCapacity, speedMove, foodAmount, eatProbability);
    }

    /**
     * Метод для получения наследников класса Herbivore
     */
    public static List<Class<? extends Animal>> getChildrenPredator() {
        return arrayChildren;
    }
}
