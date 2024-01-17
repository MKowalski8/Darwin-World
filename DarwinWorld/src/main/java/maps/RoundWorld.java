package maps;

import components.AnimalInformation;
import components.Boundary;
import components.Vector2d;
import worldElements.Animal;

public class RoundWorld extends AbstractWorld {

    public RoundWorld(Boundary bounds, int numberOfPlants , int growingPlantsNumber) {
        super(bounds, numberOfPlants, growingPlantsNumber);
    }


    @Override
    public Vector2d cellToPlaceOn(Animal animal, Boundary bounds, Vector2d position) {
        Vector2d newPosition = position.addVector(animal.moveTo().toVector2d());

        if (bounds.outOfBounds(newPosition)){
            animal.emergencyRotation();
            newPosition = position;
        } else if (bounds.crossedSites(newPosition)) {
            newPosition = bounds.transitionVector(newPosition);
        }

        return newPosition;
    }
}