package maps;

import components.AnimalInformation;
import components.Boundary;
import components.MapStatistics;
import components.Vector2d;
import simulations.MapChangeListener;
import worldElements.Animal;

import java.util.*;

public abstract class AbstractWorld implements WorldMap {

    private final Map<Vector2d, MapCell> mapCells = new HashMap<>();

    private final Map<Vector2d, Boolean> plants = new HashMap<>();

    private final List<MapChangeListener> observers = new ArrayList<>();

    private final Boundary bounds;

    private final int growingPlantsNumber;

    private final MapStatistics stats;

    public AbstractWorld(Boundary bounds, int startPlants, int growingPlantsNumber, MapStatistics stats) {
        this.bounds = bounds;
        generatePlants(startPlants);
        this.growingPlantsNumber = growingPlantsNumber;
        this.stats = stats;
    }

    private void generatePlants(int numberOfPlants) {
//        TODO
//        generowanie pozycji dla traw/roslinek i ich dodawanie
    }


    @Override
    public void moveAnimals() {
        Map<Vector2d, MapCell> newMapCells = new HashMap<>();

        mapCells.values().forEach(mapCell -> {
//            Dostajemy Animala z mapCella i umieszczamy go na kolejnym mapCellu
            for (int i = 0; i < mapCell.animalNumber(); i++) {
                Animal animal = mapCell.takeAnimalFromCell();
                animal.rotate();
//                Tutaj jest sprawdzanie kolejnego cella animala
//                zaimplementowane w zaleznosci od wariantu mapy
                Vector2d nextPosition = cellToPlaceOn(animal, bounds, mapCell.getCellPosition());
                if (!mapCells.containsKey(nextPosition)) {
                    newMapCells.put(nextPosition, new MapCell(nextPosition));
                    newMapCells.get(nextPosition).addMovedAnimal(animal);
                } else {
                    mapCells.get(nextPosition).addMovedAnimal(animal);
                }
            }
        });
        mapCells.putAll(newMapCells);
        newMapCells.clear();
        addMoved();
        removeEmptyCells();
        mapChange();
    }


    private void addMoved() {
        mapCells.values().forEach(MapCell::mergeAnimals);
    }


    //    metoda ta jest wywolywana z symulacji
    @Override
    public void placeAnimals(int numberOfAnimals, AnimalInformation animalInfo) {
        for (int i = 0; i < numberOfAnimals; i++) {
            Vector2d position = new Vector2d((int) (Math.random() * (bounds.getWidth() - 1)), (int) (Math.random() * (bounds.getHeight() - 1)));

            if (!mapCells.containsKey(position)) {
                mapCells.put(position, new MapCell(position));
            }

            mapCells.get(position).placeAnimalOnCell(new Animal(animalInfo));
        }

        mapChange();
    }


    public void cleanDeadAnimals() {
//        for (MapCell mapCell : mapCells.values()) {

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
        plants.keySet().forEach(grassPosition -> {
            if (mapCells.containsKey(grassPosition)) {
                mapCells.get(grassPosition).consumePlantOnCell();
                plants.remove(grassPosition);
            }
        });
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
        stats.updateLiveStats(getMapCellsList());
    }

    public List<MapCell> getMapCellsList() {
        return mapCells.values().stream().toList();
    }

    public Map<Vector2d, Boolean> getPlants() {return Collections.unmodifiableMap(plants);}

    public void addObserver(MapChangeListener listener) {
        observers.add(listener);
    }

    private void mapChange() {
        observers.forEach(observer -> observer.mapChanged(this));
    }
}
