package maps;

import components.AnimalInformation;
import components.Boundary;
import components.Vector2d;
import simulations.MapChangeListener;
import worldElements.Animal;

import java.util.List;
import java.util.Map;

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

    Map<Vector2d, Boolean> getPlants();
    List<MapCell> getMapCellsList();

    void addObserver(MapChangeListener listener);

}
