package maps;

import worldElements.Animal;
import java.util.ArrayList;
import java.util.List;

public class MapCell {

    private final List<Animal> animals = new ArrayList<>();

    public void placeAnimalOnCell(Animal animal){
        animals.add(animal);
    }
}
