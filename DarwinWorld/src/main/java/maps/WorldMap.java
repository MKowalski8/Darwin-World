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

    void move(Animal animal);

    void placeAnimals(int numberOfAnimals);

    Boundary getBounds();
}
