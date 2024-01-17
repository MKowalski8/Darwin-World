package maps;

import components.AnimalInformation;
import components.Boundary;
import components.Vector2d;
import worldElements.Animal;

public interface WorldMap {

    void moveAnimals();

    void placeAnimals(int numberOfAnimals, AnimalInformation animaInfo);

    Boundary getBounds();

    void cleanDeadAnimals();

    boolean areAnimals();

    Vector2d cellToPlaceOn(Animal animal, Boundary bounds, Vector2d position);

    void consumePlants();

    void reproduction();

    void plantGrow();

    void endDay();


}
