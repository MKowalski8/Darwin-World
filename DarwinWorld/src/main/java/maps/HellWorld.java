package maps;

import components.AnimalInformation;
import components.Boundary;
import components.Vector2d;
import worldElements.Animal;

public class HellWorld extends AbstractWorld {

    public HellWorld(Boundary bounds, int numberOfPlants, int growingPlantsNumber) {
        super(bounds, numberOfPlants, growingPlantsNumber);
    }


    @Override
    public Vector2d cellToPlaceOn(Animal animal, Boundary bounds, Vector2d position) {
        Vector2d newPosition = position.addVector(animal.moveTo().toVector2d());

        if (bounds.outOfBounds(newPosition) || bounds.crossedSites(newPosition)){
            newPosition = new Vector2d((int) (Math.random() * (bounds.getWidth()-1)),
                    (int) (Math.random() * (bounds.getHeight()-1)));
        }

        return newPosition;
    }
}
