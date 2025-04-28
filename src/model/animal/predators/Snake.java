package model.animal.predators;

import lombok.Getter;
import model.animal.Predator;
import model.animal.herbivores.Duck;
import model.animal.herbivores.Hare;
import model.animal.herbivores.Mouse;

import java.util.Map;

@Getter
public class Snake extends Predator {

    private static final double WEIGHT = 15;
    private static final int MAX_CAPACITY = 30;
    private static final int SPEED_MOVE = 1;
    private static final double FOOD_AMOUNT = 3;

    private static Map<Class<?>,Integer> EAT_PROBABILITY = Map.of(
            Fox.class,15,
            Hare.class,20,
            Mouse.class, 40,
            Duck.class,10
    );

    public Snake() {
        super(WEIGHT, MAX_CAPACITY, SPEED_MOVE, FOOD_AMOUNT, EAT_PROBABILITY);
    }

    @Override
    public String getEmoji() {
        return "🐍";
    }
}
