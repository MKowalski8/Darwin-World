package components;

import MapStatisticsAndInformations.Boundary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundaryTest {

    @Test
    public void checkTransitionedVector() {
    }

    @Test
    public void checkIfOutOfBounds() {
        //        given
        Boundary bounds = new Boundary(5, 5);

        //        when
        Vector2d nearLeftSite = new Vector2d(0, 0);
        Vector2d nearRightSite = new Vector2d(5, 4);
        Vector2d normal = new Vector2d(1, 3);
        Vector2d out1 = new Vector2d(3, 5);
        Vector2d out2 = new Vector2d(-1, -1);

        //        then
        assertTrue(bounds.outOfBounds(out1));
        assertTrue(bounds.outOfBounds(out2));
        assertFalse(bounds.outOfBounds(nearLeftSite));
        assertFalse(bounds.outOfBounds(nearRightSite));
        assertFalse(bounds.outOfBounds(normal));
    }

    @Test
    public void checkIfCrossedSites() {
        //        given
        Boundary bounds = new Boundary(5, 5);

        //        when
        Vector2d outLeftSite = new Vector2d(-1, 0);
        Vector2d outRightSite = new Vector2d(5, 4);
        Vector2d normal = new Vector2d(1, 3);

        assertTrue(bounds.crossedSites(outLeftSite));
        assertTrue(bounds.crossedSites(outRightSite));
        assertFalse(bounds.crossedSites(normal));


    }

    @Test
    public void checkReturnedTranstionVector() {
        //        given
        Boundary bounds = new Boundary(5, 5);
        Vector2d outLeftSite = new Vector2d(-1, 0);
        Vector2d outRightSite = new Vector2d(5, 4);


        //        when
        Vector2d leftTransitionedVector = bounds.transitionVector(outLeftSite);
        Vector2d rightTransitionedVector = bounds.transitionVector(outRightSite);
        System.out.println(leftTransitionedVector);

        //        then
        Vector2d correctOutLeftSite = new Vector2d(4, 0);
        Vector2d correctOutRightSite = new Vector2d(0, 4);

        assertEquals(correctOutLeftSite.getX(), leftTransitionedVector.getX());
        assertEquals(correctOutLeftSite.getY(), leftTransitionedVector.getY());
        assertEquals(correctOutRightSite.getX(), rightTransitionedVector.getX());
        assertEquals(correctOutRightSite.getY(), rightTransitionedVector.getY());

    }

    @Test
    public void paretoPrinciple() {
        //given
        Boundary bounds = new Boundary(17, 100);

        //then ??

        //then
        assertEquals(40, bounds.getLowerJungleBound());
        assertEquals(60, bounds.getUpperJungleBound());
    }
}