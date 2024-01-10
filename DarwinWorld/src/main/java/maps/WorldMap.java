package maps;

import components.Boundary;
import components.Vector2d;
import worldElements.WorldElement;

public interface WorldMap {
    default boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }

    WorldElement objectAt(Vector2d position);

    Boundary getBounds();
}
