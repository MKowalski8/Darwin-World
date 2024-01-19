package components;

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



}
