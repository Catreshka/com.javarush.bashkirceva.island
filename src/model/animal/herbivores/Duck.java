package model.animal.herbivores;

import lombok.Getter;
import model.Plant;
import model.animal.Herbivore;

import java.util.Map;

@Getter
public class Duck extends Herbivore {

    private static final double WEIGHT = 1;
    private static final int MAX_CAPACITY = 200;
    private static final int SPEED_MOVE = 4;
    private static final double FOOD_AMOUNT = 0.15;

    private static Map<Class<?>,Integer> EAT_PROBABILITY = Map.of(
            Plant.class,100,
            Caterpillar.class,90
    );

    public Duck() {
        super(WEIGHT, MAX_CAPACITY, SPEED_MOVE, FOOD_AMOUNT, EAT_PROBABILITY);
    }

    @Override
    public String getEmoji() {
        return "🦆";
    }
}
