package worldElements;

import MapStatisticsAndInformations.AnimalInformation;
import MapStatisticsAndInformations.GenomeInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AnimalTest {
    @Test
    public void getNumberOfUniqueDescendantsTest() {
        //given
        AnimalInformation info = new AnimalInformation(1, 1, 100, 1, 1,
                new GenomeInformation(0, 0, false, 10));

        Animal grandfather = new Animal(info);
        Animal grandmother = new Animal(info);
        Animal father = new Animal(info);
        Animal mother = grandfather.reproduce(grandmother);
        Animal uncleFromMotherSide = grandfather.reproduce(grandmother);
        Animal aunt = new Animal(info);
        Animal firstSon = father.reproduce(mother);
        Animal secondSon = father.reproduce(mother);
        Animal daughter = father.reproduce(mother);

        //when
        Animal bastard = father.reproduce(aunt);
        Animal consanguineousOffspring = firstSon.reproduce(mother);
        //dauther dies
        daughter.fixFamilyTree();

        //then
        Assertions.assertEquals(5, grandfather.getNumberOfUniqueDescendants());
        Assertions.assertEquals(4, father.getNumberOfUniqueDescendants());
        Assertions.assertEquals(3, mother.getNumberOfUniqueDescendants());
        Assertions.assertEquals(1, aunt.getNumberOfUniqueDescendants());

    }
}