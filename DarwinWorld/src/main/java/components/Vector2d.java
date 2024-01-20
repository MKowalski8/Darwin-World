package components;

import java.util.Objects;
import java.util.Vector;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

   @Override
   public String toString() {
       return String.format("(%d,%d)", x, y);
   }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public Vector2d addVector(Vector2d vectorToAdd) {
        return new Vector2d(vectorToAdd.getX() + x, vectorToAdd.getY() + y);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
