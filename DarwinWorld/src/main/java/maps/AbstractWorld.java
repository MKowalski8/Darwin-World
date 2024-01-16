package maps;

import components.Boundary;
import components.Vector2d;
import worldElements.Animal;
import worldElements.Grass;
import worldElements.WorldElement;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorld implements WorldMap {

    private final Map<Vector2d, MapCell> mapCells = new HashMap<>();
    private final Map<Vector2d, Grass> plants = new HashMap<>();
    private final Boundary bounds;

    private final int plantEnergy;

    private final int growingPlantsNumber;

    public AbstractWorld(Boundary bounds, int numberOfPlants, int plantEnergy, int growingPlantsNumber, int numberOfAnimals) {
        this.bounds = bounds;
        generatePlants(numberOfPlants);
        this.plantEnergy = plantEnergy;
        this.growingPlantsNumber = growingPlantsNumber;
    }

    private void generatePlants(int numberOfPlants) {
//        TODO
//        generowanie pozycji dla traw/roslinek i ich dodawanie
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
//        Możliwe, że do wywalenia
        return null;
    }

    @Override
    public void moveAnimals() {
        mapCells.values().forEach(mapCell -> {
//            Dostajemy Animala z mapCella i umieszczamy go na kolejnym mapCellu
            for (int i = 0; i < mapCell.animalNumber(); i++) {
                Animal animal = mapCell.takeAnimalFromCell();
//                Tutaj bedzie sprawdzanie kolejnego cella animala
//                zaimplementowane w zaleznosci od wariantu mapy
                Vector2d nextPosition = cellToPlaceOn(animal, bounds);
                if (!mapCells.containsKey(nextPosition)) {
                    mapCells.put(nextPosition, new MapCell(nextPosition));
                }
                mapCells.get(nextPosition).addMovedAnimal(animal);
            }
            addMoved();
        });
    }


    private void addMoved() {
        mapCells.values().forEach(MapCell::mergeAnimals);
    }


    //    metoda ta jest wywolywana z symulacji
    @Override
    public void placeAnimals(int numberOfAnimals) {
        for (int i = 0; i < numberOfAnimals; i++) {
            Vector2d position = new Vector2d((int) (Math.random() * bounds.width()), (int) (Math.random() * bounds.height()));

//            mapCells.containsKey(position) ? mapCells.put(position, new MapCell().placeAnimalOnCell(new Animal())) :
//                    mapCells.get(position).placeAnimalOnCell(new Animal());
        }
    }


    public void cleanDeadAnimals() {
        mapCells.values().forEach(MapCell::removeDeads);
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
                mapCells.get(grassPosition).consumePlantOnCell(plantEnergy);
            }
        });
    }

    public void reproduction() {
        mapCells.values().forEach(MapCell::initializeReproduction);
    }

    public void plantGrow() {
        generatePlants(growingPlantsNumber);
    }

    @Override
    public Boundary getBounds() {
        return bounds;
    }


    public boolean areAnimals() {
        return !mapCells.isEmpty();
    }
}
