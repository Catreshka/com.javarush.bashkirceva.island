package model.animal.herbivores;

import lombok.Getter;
import model.Plant;
import model.animal.Herbivore;

import java.util.Map;

@Getter
public class Deer extends Herbivore {

    private static final double WEIGHT = 300;
    private static final int MAX_CAPACITY = 20;
    private static final int SPEED_MOVE = 4;
    private static final double FOOD_AMOUNT = 50;

    private static Map<Class<?>,Integer> EAT_PROBABILITY = Map.of(
            Plant.class,100
    );

    public Deer() {
        super(WEIGHT, MAX_CAPACITY, SPEED_MOVE, FOOD_AMOUNT, EAT_PROBABILITY);
    }

    @Override
    public String getEmoji() {
        return "🦌";
    }
}
