package maps;

import MapStatisticsAndInformations.AnimalInformation;
import MapStatisticsAndInformations.Boundary;
import MapStatisticsAndInformations.GenomeInformation;
import MapStatisticsAndInformations.MapStatistics;
import components.Vector2d;
import org.junit.jupiter.api.Test;
import worldElements.Animal;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapCellTest {

    private final GenomeInformation genomeInfo = new GenomeInformation(10, 10, false, 10);
    private final AnimalInformation animalInfo = new AnimalInformation(10, 5,
            10, 15, 5, genomeInfo);

    @Test
    void checkIfCorrectAnimalEatPlant() {
        //given
        Animal animal1 = new Animal(animalInfo);
        Animal animal2 = new Animal(animalInfo);
        Animal animal3 = new Animal(animalInfo);
        MapCell mapCell = new MapCell(new Vector2d(0, 0));

//        set "hierarchy"
        animal1.eatPlant();
        animal1.eatPlant();
        animal2.eatPlant();
        mapCell.placeAnimalOnCell(animal1);
        mapCell.placeAnimalOnCell(animal2);
        mapCell.placeAnimalOnCell(animal3);

        //when
        mapCell.consumePlantOnCell();

        //then
        assertEquals(25, animal1.getEnergy());
        assertEquals(15, animal2.getEnergy());
        assertEquals(10, animal3.getEnergy());
    }

    @Test
    void checkIfReproductionIsDoneProperly() {
        //given
        Animal animal1 = new Animal(animalInfo);
        Animal animal2 = new Animal(animalInfo);
        Animal animal3 = new Animal(animalInfo);
        MapCell mapCell = new MapCell(new Vector2d(0, 0));
        animal1.eatPlant();
        animal1.eatPlant();
        animal2.eatPlant();
        mapCell.placeAnimalOnCell(animal1);
        mapCell.placeAnimalOnCell(animal2);
        mapCell.placeAnimalOnCell(animal3);

        //when
        mapCell.initializeReproduction();

        //then
        assertEquals(animal1.getNumberOfChildren(), 1);
        assertEquals(animal2.getNumberOfChildren(), 1);
        assertEquals(mapCell.animalNumber(), 4);
    }

    @Test
    void checkIfAnimalsAreSorted() {
        //given
        Animal animal1 = new Animal(animalInfo);
        Animal animal2 = new Animal(animalInfo);
        Animal animal3 = new Animal(animalInfo);
        Animal animal4 = new Animal(animalInfo);
        animal2.eatPlant();
        animal1.eatPlant();
        animal2.eatPlant();
        animal1.reproduce(animal4);
        animal4.eatPlant();
        MapCell mapCell = new MapCell(new Vector2d(0, 0));


        //when
        //        sorting is done while putting on cell
        mapCell.placeAnimalOnCell(animal1);
        mapCell.placeAnimalOnCell(animal2);
        mapCell.placeAnimalOnCell(animal3);
        mapCell.placeAnimalOnCell(animal4);
        List<Animal> animals = mapCell.getAnimals();

        //then
        assertEquals(animal3, animals.get(0));
        assertEquals(animal4, animals.get(1));
//        the same energy, but animal4 is "higher" in hierarchy
        assertEquals(animal4.getEnergy(), animal3.getEnergy());


        assertEquals(animal1, animals.get(2));
        assertEquals(animal2, animals.get(3));
    }

    @Test
    void checkIfDeadAnimalsAreTakenFromCell() {
        //given
        Animal animal1 = new Animal(animalInfo);
        Animal animal2 = new Animal(animalInfo);
        Animal animal3 = new Animal(animalInfo);
        Animal animal4 = new Animal(animalInfo);

        MapCell mapCell = new MapCell(new Vector2d(0, 0));
        mapCell.placeAnimalOnCell(animal1);
        mapCell.placeAnimalOnCell(animal2);
        mapCell.placeAnimalOnCell(animal3);
        mapCell.placeAnimalOnCell(animal4);

        //when
//        will not be dead
        animal1.consumeEnergyToReproduce();
//        will be dead
        animal3.skipDay();
        animal2.skipDay();

        mapCell.removeDeads();
        
        //then
        assertEquals(2, mapCell.animalNumber());
    }
}