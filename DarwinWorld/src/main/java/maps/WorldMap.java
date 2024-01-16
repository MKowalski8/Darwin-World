package maps;

import components.Boundary;
import components.Vector2d;
import worldElements.Animal;
import worldElements.WorldElement;

public interface WorldMap {
    default boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }

    WorldElement objectAt(Vector2d position);

    void moveAnimals();

    void placeAnimals(int numberOfAnimals);

    Boundary getBounds();

    void cleanDeadAnimals();

    boolean areAnimals();

    Vector2d cellToPlaceOn(Animal animal);

    void consumePlants();

    void reproduction();

    void plantGrow();
}
