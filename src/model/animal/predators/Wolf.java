package model.animal.predators;

import lombok.Getter;
import model.animal.Predator;
import model.animal.herbivores.*;

import java.util.Map;

@Getter
public class Wolf extends Predator {

    private static final double WEIGHT = 50;
    private static final int MAX_CAPACITY = 30;
    private static final int SPEED_MOVE = 3;
    private static final double FOOD_AMOUNT = 8;

    private static Map<Class<?>,Integer> EAT_PROBABILITY = Map.of(
            Horse.class,10,
            Deer.class,15,
            Hare.class,60,
            Mouse.class, 80,
            Goat.class,60,
            Sheep.class,70,
            Duck.class,40
    );

    public Wolf() {
        super(WEIGHT, MAX_CAPACITY, SPEED_MOVE, FOOD_AMOUNT, EAT_PROBABILITY);
    }

    @Override
    public String getEmoji() {
        return "🐺";
    }
}
