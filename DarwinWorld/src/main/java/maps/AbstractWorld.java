package maps;

import components.Boundary;
import components.Vector2d;
import worldElements.WorldElement;

public abstract class AbstractWorld implements WorldMap{

    private final Boundary bounds;
    private int numberOfPlants;

    private int plantEnergy;

    private int growingPlantsNumber;

    private int numberOfAnimals;

    public AbstractWorld(int width, int height, int numberOfPlants, int plantEnergy, int growingPlantsNumber, int numberOfAnimals) {
        this.bounds = new Boundary(width, height);
        this.numberOfPlants = numberOfPlants;
        this.plantEnergy = plantEnergy;
        this.growingPlantsNumber = growingPlantsNumber;
        this.numberOfAnimals = numberOfAnimals;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return null;
    }

    @Override
    public Boundary getBounds(){
        return bounds;
    }

    
}
