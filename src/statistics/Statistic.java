package statistics;

/**
 * Класс для вывода статистики
 */

import lombok.Getter;
import model.Island;
import model.animal.Animal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Statistic {

    private static final ConcurrentHashMap<Class<?>, AtomicInteger> animalsCount = new ConcurrentHashMap<>();

    private static final AtomicInteger totalDeathsByEnergy = new AtomicInteger(0);
    private static final AtomicInteger totalDeathsByPredation = new AtomicInteger(0);
    private static final AtomicInteger totalBirths = new AtomicInteger(0);

    /**
     * Метод для подсчета количества растений на острове
     */
    public static int countTotalPlants(Island island) {
        int totalPlants = 0;
        for (int i = 0; i < island.getWidthIsland(); i++) {
            for (int j = 0; j < island.getLengthIsland(); j++) {
                totalPlants = totalPlants + island.getCell(i,j).countPlants();
            }
        }
        return totalPlants;
    }

    /**
     * Метод для подсчета количества умерших животных от недостатка энергии
     */
    public static void addAnimalInDeathsByEnergyStatistic(Animal animal) {
        animalsCount.computeIfPresent(animal.getClass(), (k, v) -> {
            v.decrementAndGet();
            return v.get() <= 0 ? null : v;
        });
        totalDeathsByEnergy.incrementAndGet();
    }

    /**
     * Метод для подсчета количества съеденных животных
     */
    public static void addAnimalInDeathsByPredationStatistic(Animal animal) {
        animalsCount.computeIfPresent(animal.getClass(), (k, v) -> {
            v.decrementAndGet();
            return v.get() <= 0 ? null : v;
        });
        totalDeathsByPredation.incrementAndGet();
    }

    /**
     * Метод для подсчета количества новорожденных
     */
    public static void addAnimalInBirthStatistic(Animal animal) {
        animalsCount.computeIfAbsent(animal.getClass(), k -> new AtomicInteger(0)).incrementAndGet();
        totalBirths.incrementAndGet();
    }

    /**
     * Метод для вывода статистики
     */
    public static void printStatistics(Island island) {
        System.out.println("------- Статистика -------");
        System.out.println("Растений " + countTotalPlants(island));
        System.out.println("Животных: " + animalsCount
                .values()
                .stream()
                .mapToLong(AtomicInteger::get)
                .sum());
        System.out.println("[");
        animalsCount.forEach((cls,count) -> {
            try {
                String emoji = ((Animal)cls.getDeclaredConstructor().newInstance()).getEmoji();
                System.out.println(emoji + "-" + count.get());
            } catch (Exception e) {
                System.out.println(cls.getSimpleName() + "- " + count.get());
            }
        });
        System.out.println("]");
        System.out.println("Новорожденных " + totalBirths.get());
        System.out.println("Умерших " + totalDeathsByEnergy.get());
        System.out.println("Съеденных " + totalDeathsByPredation.get());
    }
}
