package components;

public class Boundary {

    private final int width;
    private final int height;

    public Boundary(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
