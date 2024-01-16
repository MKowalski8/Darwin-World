package components;

import java.util.Vector;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean outOfBounds(Boundary bounds){
        return (bounds.height() < x || x < -1);
    }

}
