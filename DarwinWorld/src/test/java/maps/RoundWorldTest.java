package maps;

import MapStatisticsAndInformations.AnimalInformation;
import MapStatisticsAndInformations.Boundary;
import MapStatisticsAndInformations.GenomeInformation;
import MapStatisticsAndInformations.MapStatistics;
import components.Genome;
import components.MapDirection;
import components.Vector2d;
import org.junit.jupiter.api.Test;
import worldElements.Animal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundWorldTest {

    @Test
    void checkIfCrossSitesCorrectly() {
        // given
        GenomeInformation genomeInfo = new GenomeInformation(1, 1, false, 1);
        AnimalInformation animalInfo = new AnimalInformation(4, 2,
                5, 1, 1, genomeInfo);
        Genome genome = new Genome(genomeInfo);

        Animal animal1 = new Animal(animalInfo, MapDirection.EAST, genome, 0);
        Animal animal2 = new Animal(animalInfo, MapDirection.WEST, genome, 0);
        Animal animal3 = new Animal(animalInfo, MapDirection.NORTH_EAST, genome, 0);
        Animal animal4 = new Animal(animalInfo, MapDirection.SOUTH_EAST, genome, 0);
        Animal animal5 = new Animal(animalInfo, MapDirection.NORTH_WEST, genome, 0);
        Animal animal6 = new Animal(animalInfo, MapDirection.SOUTH_WEST, genome, 0);

        Boundary bounds = new Boundary(5, 5);
        WorldMap map = new RoundWorld(bounds, 1, 1, new MapStatistics());

        //when
        Vector2d newPositionWest = map.cellToPlaceOn(animal1, bounds, new Vector2d(4, 2));
        Vector2d newPositionEast = map.cellToPlaceOn(animal2, bounds, new Vector2d(0, 3));
        Vector2d northEastNewPosition = map.cellToPlaceOn(animal3, bounds, new Vector2d(4, 2));
        Vector2d southEastNewPosition = map.cellToPlaceOn(animal4, bounds, new Vector2d(4, 2));
        Vector2d northWestNewPosition = map.cellToPlaceOn(animal5, bounds, new Vector2d(0, 2));
        Vector2d southWestNewPosition = map.cellToPlaceOn(animal6, bounds, new Vector2d(0, 2));


        //then
        assertEquals(newPositionWest, new Vector2d(0, 2));
        assertEquals(newPositionEast, new Vector2d(4, 3));
        assertEquals(northEastNewPosition, new Vector2d(0, 3));
        assertEquals(southEastNewPosition, new Vector2d(0, 1));
        assertEquals(northWestNewPosition, new Vector2d(4, 3));
        assertEquals(southWestNewPosition, new Vector2d(4, 1));
    }

    @Test
    void checkIfNotPassSitesWhileBound() {
        // given
        GenomeInformation genomeInfo = new GenomeInformation(1, 1, false, 1);
        AnimalInformation animalInfo = new AnimalInformation(4, 2,
                5, 1, 1, genomeInfo);
        Genome genome = new Genome(genomeInfo);

        Animal animalNorth = new Animal(animalInfo, MapDirection.NORTH, genome, 0);
        Animal animalSouth = new Animal(animalInfo, MapDirection.SOUTH, genome, 0);
        Animal animalNorthEast = new Animal(animalInfo, MapDirection.NORTH_EAST, genome, 0);
        Animal animalSouthEast = new Animal(animalInfo, MapDirection.SOUTH_EAST, genome, 0);
        Animal animalNorthWest = new Animal(animalInfo, MapDirection.NORTH_WEST, genome, 0);
        Animal animalSouthWest = new Animal(animalInfo, MapDirection.SOUTH_WEST, genome, 0);

        Boundary bounds = new Boundary(5, 5);
        WorldMap map = new RoundWorld(bounds, 1, 1, new MapStatistics());

        //when
        Vector2d newPositionNorth = map.cellToPlaceOn(animalNorth, bounds, new Vector2d(3, 4));
        Vector2d newPositionSouth = map.cellToPlaceOn(animalSouth, bounds, new Vector2d(3, 0));
        Vector2d northEastNewPosition = map.cellToPlaceOn(animalNorthEast, bounds, new Vector2d(4, 4));
        Vector2d southEastNewPosition = map.cellToPlaceOn(animalSouthEast, bounds, new Vector2d(4, 0));
        Vector2d northWestNewPosition = map.cellToPlaceOn(animalNorthWest, bounds, new Vector2d(0, 4));
        Vector2d southWestNewPosition = map.cellToPlaceOn(animalSouthWest, bounds, new Vector2d(0, 0));


        //then
        assertEquals(newPositionNorth, new Vector2d(3, 4));
        assertEquals(newPositionSouth, new Vector2d(3, 0));
        assertEquals(northEastNewPosition, new Vector2d(4, 4));
        assertEquals(southEastNewPosition, new Vector2d(4, 0));
        assertEquals(northWestNewPosition, new Vector2d(0, 4));
        assertEquals(southWestNewPosition, new Vector2d(0, 0));
    }

    @Test
    void checkIfBoundCorrectly() {
        // given
        GenomeInformation genomeInfo = new GenomeInformation(1, 1, false, 1);
        AnimalInformation animalInfo = new AnimalInformation(4, 2,
                5, 1, 1, genomeInfo);
        Genome genome = new Genome(genomeInfo);

        Animal animalNorth = new Animal(animalInfo, MapDirection.NORTH, genome, 0);
        Animal animalSouth = new Animal(animalInfo, MapDirection.SOUTH, genome, 0);
        Animal animalNorthEast = new Animal(animalInfo, MapDirection.NORTH_EAST, genome, 0);
        Animal animalSouthEast = new Animal(animalInfo, MapDirection.SOUTH_EAST, genome, 0);
        Animal animalNorthWest = new Animal(animalInfo, MapDirection.NORTH_WEST, genome, 0);
        Animal animalSouthWest = new Animal(animalInfo, MapDirection.SOUTH_WEST, genome, 0);

        Boundary bounds = new Boundary(5, 5);
        WorldMap map = new RoundWorld(bounds, 1, 1, new MapStatistics());

        //when
        map.cellToPlaceOn(animalNorth, bounds, new Vector2d(3, 4));
        map.cellToPlaceOn(animalSouth, bounds, new Vector2d(3, 0));
        map.cellToPlaceOn(animalNorthEast, bounds, new Vector2d(4, 4));
        map.cellToPlaceOn(animalSouthEast, bounds, new Vector2d(4, 0));
        map.cellToPlaceOn(animalNorthWest, bounds, new Vector2d(0, 4));
        map.cellToPlaceOn(animalSouthWest, bounds, new Vector2d(0, 0));


        //then
        assertEquals(MapDirection.SOUTH, animalNorth.getFacing());
        assertEquals(MapDirection.NORTH, animalSouth.getFacing());
        assertEquals(MapDirection.SOUTH_WEST, animalNorthEast.getFacing());
        assertEquals(MapDirection.NORTH_WEST, animalSouthEast.getFacing());
        assertEquals(MapDirection.SOUTH_EAST, animalNorthWest.getFacing());
        assertEquals(MapDirection.NORTH_EAST, animalSouthWest.getFacing());
    }
}