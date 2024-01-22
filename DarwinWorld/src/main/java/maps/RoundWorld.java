package maps;

import MapStatisticsAndInformations.Boundary;
import MapStatisticsAndInformations.MapStatistics;
import components.Vector2d;
import worldElements.Animal;

public class RoundWorld extends AbstractWorld {

    public RoundWorld(Boundary bounds, int numberOfPlants , int growingPlantsNumber, MapStatistics statistics) {
        super(bounds, numberOfPlants, growingPlantsNumber, statistics);
    }


    @Override
    public Vector2d cellToPlaceOn(Animal animal, Boundary bounds, Vector2d position) {
        Vector2d newPosition = position.addVector(animal.getFacing().toVector2d());

        if (bounds.outOfBounds(newPosition)){
            animal.emergencyRotation();
            newPosition = position;
        } else if (bounds.crossedSites(newPosition)) {
            newPosition = bounds.transitionVector(newPosition);
        }
        return newPosition;
    }
}