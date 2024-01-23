package maps;

import MapStatisticsAndInformations.AnimalInformation;
import MapStatisticsAndInformations.GenomeInformation;
import components.Vector2d;
import org.junit.jupiter.api.Test;
import worldElements.Animal;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapCellTest {

    private GenomeInformation genomeInfo = new GenomeInformation(10, 10, false, 10);
    private AnimalInformation animalInfo = new AnimalInformation(10, 0,
            10, 0, 10, genomeInfo);



    @Test
    void consumePlantOnCell() {
    }

    @Test
    void initializeReproduction() {
    }

    @Test
    void takeAnimalFromCell() {
    }

    @Test
    void sortAnimals() {
        //given
        Animal animal1 = new Animal(animalInfo);
        Animal animal2 = new Animal(animalInfo);
        Animal animal3 = new Animal(animalInfo);
        animal2.eatPlant();
        animal1.eatPlant();
        animal2.eatPlant();
        MapCell mapCell = new MapCell(new Vector2d(0,0));
        mapCell.placeAnimalOnCell(animal1); mapCell.placeAnimalOnCell(animal2); mapCell.placeAnimalOnCell(animal3);

        //when
        List<Animal> animals = mapCell.getAnimals();

        //then
        assertEquals(animal3, animals.get(0));
        assertEquals(animal1, animals.get(1));
        assertEquals(animal2, animals.get(2));
    }
}