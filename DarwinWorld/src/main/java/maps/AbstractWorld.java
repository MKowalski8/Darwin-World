package maps;

import components.Boundary;
import components.Vector2d;
import worldElements.Animal;
import worldElements.Grass;
import worldElements.WorldElement;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorld implements WorldMap{

    private final Map<Vector2d, MapCell> mapCells = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final Boundary bounds;
    private int numberOfPlants;

    private int plantEnergy;

    private int growingPlantsNumber;

    public AbstractWorld(Boundary bounds, int numberOfPlants, int plantEnergy, int growingPlantsNumber, int numberOfAnimals) {
        this.bounds = bounds;
        this.numberOfPlants = numberOfPlants;
        this.plantEnergy = plantEnergy;
        this.growingPlantsNumber = growingPlantsNumber;
        placeAnimals(numberOfAnimals);
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
//        Możliwe, że do wywalenia
        return null;
    }


    @Override
    public void move(Animal animal) {
//        TODO
    }

    @Override
    public void placeAnimals(int numberOfAnimals){
        for (int i = 0; i < numberOfAnimals; i++){
            Vector2d position = new Vector2d((int)(Math.random() * bounds.width()),  (int)(Math.random() * bounds.height()));
            
//            mapCells.containsKey(position) ? mapCells.put(position, new MapCell().placeAnimalOnCell(new Animal())) :
//                    mapCells.get(position).placeAnimalOnCell(new Animal());
        }
    }

    @Override
    public Boundary getBounds(){
        return bounds;
    }

    
}
