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

import static org.junit.jupiter.api.Assertions.*;

class HellWorldTest {

    @Test
    void checkIfEnergyIsTakenWhileTeleporting() {
        // given
        GenomeInformation genomeInfo =  new GenomeInformation(1,1, false, 1);
        AnimalInformation animalInfo = new AnimalInformation(4, 2,
                5,1, 1, genomeInfo);
        Genome genome = new Genome(genomeInfo);

        Animal animal1 = new Animal(animalInfo, MapDirection.NORTH, genome, 0);
        Animal animal2 = new Animal(animalInfo, MapDirection.WEST, genome, 0);
        Animal animal3 = new Animal(animalInfo, MapDirection.SOUTH, genome, 0);
        Animal animal4 = new Animal(animalInfo, MapDirection.EAST, genome, 0);

        Boundary bounds = new Boundary(1,1);
        WorldMap map = new HellWorld(bounds, 1, 1, new MapStatistics());

        //when
        Vector2d position = map.cellToPlaceOn(animal1, bounds, new Vector2d(-10,-10));
        map.cellToPlaceOn(animal2, bounds, new Vector2d(5,5));
        map.cellToPlaceOn(animal3, bounds, new Vector2d(0,0));
        map.cellToPlaceOn(animal4, bounds, new Vector2d(0,0));

        //then
        assertEquals(2,animal1.getEnergy());
        assertEquals(2,animal2.getEnergy());
        assertEquals(2,animal3.getEnergy());
        assertEquals(2,animal4.getEnergy());
        assertEquals(position, new Vector2d(0,0));
    }

    @Test
    void checkIfTeleportCorrectly(){
        GenomeInformation genomeInfo =  new GenomeInformation(1,1, false, 1);
        AnimalInformation animalInfo = new AnimalInformation(4, 2,
                5,1, 1, genomeInfo);
        Animal animal1 = new Animal(animalInfo);
        Animal animal2 = new Animal(animalInfo);

        Boundary bounds = new Boundary(1,1);
        WorldMap map = new HellWorld(bounds, 1, 1, new MapStatistics());

        //when
        Vector2d position1 = map.cellToPlaceOn(animal1, bounds, new Vector2d(-10,-10));
        Vector2d position2 = map.cellToPlaceOn(animal2, bounds, new Vector2d(0,0));

        //then
        assertEquals(position1, new Vector2d(0,0));
        assertEquals(position2, new Vector2d(0,0));
    }
}