package components;

public class Boundary {

    private final int width;
    private final int height;
    private final int upperJoungleBound;
    private final int lowerJoungleBound;

    public Boundary(int width, int height) {
        this.width = width;
        this.height = height;
        this.lowerJoungleBound=(int) (height*0.40);
        this.upperJoungleBound=(int) (height*0.60);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getUpperJoungleBound() {
        return upperJoungleBound;
    }

    public int getLowerJoungleBound() {
        return lowerJoungleBound;
    }

    public boolean outOfBounds(Vector2d position) {
        return (height <= position.getY() || position.getY() < 0);
    }

    public boolean crossedSites(Vector2d position) {
        return (width <= position.getX() || position.getX() < 0);
    }

    public Vector2d transitionVector(Vector2d position) {
        int newX = position.getX() < 0 ? width - 1 : 0;
        return new Vector2d(newX, position.getY());
    }
}
