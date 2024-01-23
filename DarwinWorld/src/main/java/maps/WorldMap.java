package maps;

import MapStatisticsAndInformations.AnimalInformation;
import MapStatisticsAndInformations.Boundary;
import MapStatisticsAndInformations.MapStatistics;
import components.Vector2d;
import simulations.MapChangeListener;
import worldElements.Animal;

import java.util.List;
import java.util.Set;

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

    Set<Vector2d> getPlants();
    
    List<MapCell> getMapCellsList();

    void addObserver(MapChangeListener listener);

    MapStatistics getMapStatistics();
}
