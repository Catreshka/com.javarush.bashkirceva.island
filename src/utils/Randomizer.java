package utils;

/**
 * Рандомайзер
 */

import model.animal.Animal;
import model.animal.Herbivore;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class Randomizer {

    private static final Random random = new Random();

    private Randomizer() {
    }

    /**
     * Генерация вероятности
     */
    public static boolean getProbability(double percent) {
        return ThreadLocalRandom.current().nextDouble(100) < percent;
    }

    /**
     * Генерация целого числа до max
     */
    public static int getInt(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    /**
     * Выбор рандомного значения из листа
     */
    public static <T> T randomItem(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список не может быть пустым");
        }
        return list.get(getInt(list.size()));
    }

    /**
     * Выбор рандомного класса из листа
     */
    public static Class randomClass(List<Class<? extends Animal>> list) {
        if (list == null || list.isEmpty()) {
            Logs.logInfo("Список не может быть пустым");
            throw new IllegalArgumentException("Список не может быть пустым");
        }
        return list.get(Randomizer.getInt(list.size()));
    }

    /**
     * Выбор случайного значения из перечисления (enum)
     */
    public static String randomDirection() {
        String[] directions = {"LEFT", "RIGHT", "TOP", "BOTTOM"};
        String direction = directions[random.nextInt(directions.length)];
        return direction;
    }
}
