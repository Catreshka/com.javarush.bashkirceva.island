package model.animal.herbivores;

import lombok.Getter;
import model.Plant;
import model.animal.Herbivore;

import java.util.Map;

@Getter
public class Mouse extends Herbivore {

    private static final double WEIGHT = 0.05;
    private static final int MAX_CAPACITY = 500;
    private static final int SPEED_MOVE = 1;
    private static final double FOOD_AMOUNT = 0.01;

    private static Map<Class<?>,Integer> EAT_PROBABILITY = Map.of(
            Caterpillar.class,90,
            Plant.class,100
    );

    public Mouse() {
        super(WEIGHT, MAX_CAPACITY, SPEED_MOVE, FOOD_AMOUNT, EAT_PROBABILITY);
    }

    @Override
    public String getEmoji() {
        return "🐁";
    }
}
