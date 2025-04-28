package model.animal.herbivores;

import lombok.Getter;
import model.Plant;
import model.animal.Herbivore;

import java.util.Map;

@Getter
public class Caterpillar extends Herbivore {

    private static final double WEIGHT = 0.01;
    private static final int MAX_CAPACITY = 1000;
    private static final int SPEED_MOVE = 0;
    private static final double FOOD_AMOUNT = 0;

    private static Map<Class<?>,Integer> EAT_PROBABILITY = Map.of(
            Plant.class,100
    );

    public Caterpillar() {
        super(WEIGHT, MAX_CAPACITY, SPEED_MOVE, FOOD_AMOUNT, EAT_PROBABILITY);
    }

    @Override
    public String getEmoji() {
        return "🐛";
    }
}
