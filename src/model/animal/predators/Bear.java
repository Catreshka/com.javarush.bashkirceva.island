package model.animal.predators;

import lombok.Getter;
import model.animal.Predator;
import model.animal.herbivores.*;

import java.util.Map;

@Getter
public class Bear extends Predator {

    private static final double WEIGHT = 500;
    private static final int MAX_CAPACITY = 5;
    private static final int SPEED_MOVE = 2;
    private static final double FOOD_AMOUNT = 80;

    private static Map<Class<?>,Integer> EAT_PROBABILITY = Map.of(
            Horse.class,40,
            Deer.class,80,
            Hare.class,80,
            Goat.class,70,
            Sheep.class,70,
            Duck.class,10
    );

    public Bear() {
        super(WEIGHT, MAX_CAPACITY, SPEED_MOVE, FOOD_AMOUNT, EAT_PROBABILITY);
    }

    @Override
    public String getEmoji() {
        return "🐻";
    }
}
