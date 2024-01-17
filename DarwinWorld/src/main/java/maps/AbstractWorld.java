package maps;

import components.AnimalInformation;
import components.Boundary;
import components.MapStatistics;
import components.Vector2d;
import worldElements.Animal;
import worldElements.Grass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractWorld implements WorldMap {

    private final Map<Vector2d, MapCell> mapCells = new HashMap<>();

    private final Map<Vector2d, Grass> plants = new HashMap<>();

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
        addMoved();
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
    }


    public void cleanDeadAnimals() {
//        for (MapCell mapCell : mapCells.values()) {

        mapCells.values().forEach(mapCell -> {
            List<Animal> deadAnimals = mapCell.removeDeads();
            stats.updateDeadLiftime(deadAnimals);
        });
        removeEmptyCells();
    }


    private void removeEmptyCells() {
        mapCells.values().forEach(mapCell -> {
            if (mapCell.animalNumber() == 0) {
                mapCells.get(mapCell.getCellPosition());
            }
        });
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
        mapCells.values().forEach(MapCell::initializeReproduction);
        stats.updateLiveStats(getMapCells());
    }


    public List<MapCell> getMapCells() {
        return mapCells.values().stream().toList();
    }
}
