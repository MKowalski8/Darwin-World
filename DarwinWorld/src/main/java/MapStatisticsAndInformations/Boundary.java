package MapStatisticsAndInformations;

import components.Vector2d;

import java.util.LinkedList;
import java.util.List;

public class Boundary {

    private final int width;
    private final int height;
    private final int upperJungleBound;
    private final int lowerJungleBound;

    public Boundary(int width, int height) {
        this.width = width;
        this.height = height;
        this.lowerJungleBound = (int) (height * 0.40);
        this.upperJungleBound = (int) (height * 0.60);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getUpperJungleBound() {
        return upperJungleBound;
    }

    public int getLowerJungleBound() {
        return lowerJungleBound;
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

    public List<Vector2d> getJungleList() {
        List<Vector2d> jungleCells = new LinkedList<>();
        for (int i = 0; i < width; i++) {
            for (int j = lowerJungleBound; j < upperJungleBound; j++) {
                jungleCells.add(new Vector2d(i, j));
            }
        }

        return jungleCells;
    }
}
