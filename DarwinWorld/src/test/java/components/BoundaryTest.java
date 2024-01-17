package components;

import maps.RoundWorld;
import maps.WorldMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundaryTest {

    @Test
    public void checkTransitionedVector() {
    }

    @Test
    public void checkIfOutOfBounds() {
//        given

        Boundary bounds = new Boundary(5,5);

        Vector2d nearLeftSite = new Vector2d(0,0);
        Vector2d nearRightSite = new Vector2d(5,4);
        Vector2d normal = new Vector2d(1,3);
        Vector2d out1 = new Vector2d(3,5);
        Vector2d out2 = new Vector2d(-1,-1);

        assertTrue(bounds.outOfBounds(out1));
        assertTrue(bounds.outOfBounds(out2));
        assertFalse(bounds.outOfBounds(nearLeftSite));
        assertFalse(bounds.outOfBounds(nearRightSite));
        assertFalse(bounds.outOfBounds(normal));
    }

    @Test
    public void checkIfCrossedSites() {

    }

}