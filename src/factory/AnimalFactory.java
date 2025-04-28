package factory;

/**
 * Создание любого животного с помощью reflection API
 */

import lombok.Getter;
import model.animal.Animal;
import utils.Logs;

import java.lang.reflect.Constructor;

@Getter
public class AnimalFactory {

    public <T extends Animal> T createAnimal(Class<T> animalClass) {
        try {
            Constructor<T> constructor = animalClass.getConstructor();
            if (constructor == null)
            {
                throw new NullPointerException();
            }
            return constructor.newInstance();
        }
        catch (Exception ex)
        {
            Logs.logError("Ошибка при создании животного", ex);
            return null;
        }
    }
}
