package maps;

import MapStatisticsAndInformations.AnimalInformation;
import MapStatisticsAndInformations.Boundary;
import MapStatisticsAndInformations.MapStatistics;
import components.*;
import simulations.MapChangeListener;
import worldElements.Animal;

import java.util.*;

public abstract class AbstractWorld implements WorldMap {

    private final Map<Vector2d, MapCell> mapCells = new HashMap<>();

    private final List<Vector2d> plants = new ArrayList<>();

    private final List<MapChangeListener> observers = new ArrayList<>();

    private final Boundary bounds;

    private final int growingPlantsNumber;

    private final MapStatistics stats;
    private final PlantGenerator plantGenerator;

    public AbstractWorld(Boundary bounds, int startPlants, int growingPlantsNumber, MapStatistics stats) {
        this.bounds = bounds;
        this.plantGenerator = new PlantGenerator(bounds);
        generatePlants(startPlants);
        this.growingPlantsNumber = growingPlantsNumber;
        this.stats = stats;
    }

    private void generatePlants(int numberOfPlants) {
        plants.addAll(plantGenerator.generatePlants(new HashSet<>(getPlants()), numberOfPlants));
    }


    @Override
    public void moveAnimals() {
        Map<Vector2d, MapCell> newMapCells = new HashMap<>();

        mapCells.values().forEach(mapCell -> {
            for (int i = 0; i < mapCell.animalNumber(); i++) {
                Animal animal = mapCell.takeAnimalFromCell();
                animal.rotate();

                Vector2d nextPosition = cellToPlaceOn(animal, bounds, mapCell.getCellPosition());

                if (!mapCells.containsKey(nextPosition)) {
                    putInNewMapCell(newMapCells, nextPosition, animal);
                } else {
                    mapCells.get(nextPosition).addMovedAnimal(animal);
                }
            }
        });

        mapCells.putAll(newMapCells);
        addMoved();
        cleanDeadAnimals();
    }

    private void putInNewMapCell(Map<Vector2d, MapCell> newMapCells, Vector2d nextPosition, Animal animal) {
        if (!newMapCells.containsKey(nextPosition)) {
            newMapCells.put(nextPosition, new MapCell(nextPosition));
        }
        newMapCells.get(nextPosition).addMovedAnimal(animal);
    }

    private void addMoved() {
        mapCells.values().forEach(MapCell::mergeAnimals);
    }

    @Override
    public void placeAnimals(int numberOfAnimals, AnimalInformation animalInfo) {
        for (int i = 0; i < numberOfAnimals; i++) {
            Vector2d position = new Vector2d((int) (Math.random() * (bounds.getWidth() - 1)), (int) (Math.random() * (bounds.getHeight() - 1)));

            if (!mapCells.containsKey(position)) {
                mapCells.put(position, new MapCell(position));
            }

            mapCells.get(position).placeAnimalOnCell(new Animal(animalInfo));
        }

        stats.updateLiveStats(getMapCellsList(), plants, bounds);
    }


    public void cleanDeadAnimals() {
        mapCells.values().forEach(mapCell -> {
            List<Animal> deadAnimals = mapCell.removeDeads();
            stats.updateDeadLifetime(deadAnimals);
        });
        removeEmptyCells();
    }


    private void removeEmptyCells() {
        mapCells.values().removeIf(mapCell -> mapCell.animalNumber() == 0);
    }

    public void consumePlants() {
        plants.forEach(plantPosition -> {
            if (mapCells.containsKey(plantPosition)) {
                mapCells.get(plantPosition).consumePlantOnCell();
            }
        });

        plants.removeIf(mapCells::containsKey);

    }

    @Override
    public void reproduction() {
        mapCells.values().forEach(MapCell::initializeReproduction);
    }

    @Override
    public void plantGrow() {
        generatePlants(growingPlantsNumber);
    }

    @Override
    public Boundary getBounds() {
        return bounds;
    }

    @Override
    public boolean areAnimals() {
        return !mapCells.isEmpty();
    }

    @Override
    public void endDay() {
        mapCells.values().forEach(MapCell::survivedDay);
        mapChange();
        stats.updateLiveStats(getMapCellsList(), plants, bounds);
    }

    public List<MapCell> getMapCellsList() {
        return mapCells.values().stream().toList();
    }

    public List<Vector2d> getPlants() {
        return Collections.unmodifiableList(plants);
    }

    public void addObserver(MapChangeListener listener) {
        observers.add(listener);
    }

    private void mapChange() {
        observers.forEach(observer -> observer.mapChanged(stats));
    }

    public MapStatistics getMapStatistics() {
        return stats;
    }
}
