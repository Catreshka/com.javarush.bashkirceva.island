package model.animal.predators;

import lombok.Getter;
import model.animal.Predator;
import model.animal.herbivores.*;

import java.util.Map;

@Getter
public class Earl extends Predator {

    private static final double WEIGHT = 6;
    private static final int MAX_CAPACITY = 20;
    private static final int SPEED_MOVE = 3;
    private static final double FOOD_AMOUNT = 1;

    private static Map<Class<?>,Integer> EAT_PROBABILITY = Map.of(
            Fox.class,10,
            Hare.class,90,
            Mouse.class, 90,
            Duck.class,80
    );

    public Earl() {
        super(WEIGHT, MAX_CAPACITY, SPEED_MOVE, FOOD_AMOUNT, EAT_PROBABILITY);
    }

    @Override
    public String getEmoji() {
        return "🦅";
    }
}
