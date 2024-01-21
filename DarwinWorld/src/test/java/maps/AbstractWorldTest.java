package maps;

import components.AnimalInformation;
import components.Boundary;
import components.GenomeInformation;
import components.MapStatistics;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;


class AbstractWorldTest {

    @Test
    void checkIfAnimalsNotDisappearWhileMoving() {
        //given
        int givenAnimalNumber = 1000;

        GenomeInformation genomeInfo =  new GenomeInformation(10,10, false, 10);
        AnimalInformation animalInfo = new AnimalInformation(10, 10,
                50,0, 10, genomeInfo);

        WorldMap map = new RoundWorld(new Boundary(2,2), 5, 5, new MapStatistics());

        //when
        map.placeAnimals(givenAnimalNumber, animalInfo);
        map.moveAnimals();

        //then
        List<MapCell> mapCellsList = map.getMapCellsList();
        int animalNumber = mapCellsList.stream().mapToInt(MapCell::animalNumber).sum();
        assertEquals(givenAnimalNumber, animalNumber);
    }

}