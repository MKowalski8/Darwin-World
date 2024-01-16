package maps;

import components.Boundary;
import components.Vector2d;
import worldElements.Animal;

public class HellWorld extends AbstractWorld {

    public HellWorld(Boundary bounds, int numberOfPlants, int plantEnergy, int growingPlantsNumber, int numberOfAnimals) {
        super(bounds, numberOfPlants, plantEnergy, growingPlantsNumber, numberOfAnimals);
    }


    @Override
    public Vector2d cellToPlaceOn(Animal animal) {
//        TODO
        return null;
    }
}
