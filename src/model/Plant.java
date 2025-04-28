package model;

/**
 * Класс для растений
 */

import lombok.Getter;

@Getter
public class Plant {

    public static final double WEIGHT = 1;
    public static final int MAX_CAPACITY = 200;

    /**
     * Абстрактный метод получения emoji
     */
    public String getEmoji() {
        return "☘️";
    }
}
