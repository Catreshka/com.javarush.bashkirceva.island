package model.animal.predators;

import lombok.Getter;
import model.animal.Predator;
import model.animal.herbivores.Caterpillar;
import model.animal.herbivores.Duck;
import model.animal.herbivores.Hare;
import model.animal.herbivores.Mouse;

import java.util.Map;

@Getter
public class Fox extends Predator {

    private static final double WEIGHT = 8;
    private static final int MAX_CAPACITY = 30;
    private static final int SPEED_MOVE = 2;
    private static final double FOOD_AMOUNT = 2;

    private static Map<Class<?>,Integer> EAT_PROBABILITY = Map.of(
            Caterpillar.class,40,
            Hare.class,70,
            Mouse.class, 90,
            Duck.class,60
    );

    public Fox() {
        super(WEIGHT, MAX_CAPACITY, SPEED_MOVE, FOOD_AMOUNT, EAT_PROBABILITY);
    }

    @Override
    public String getEmoji() {
        return "🦊";
    }
}
