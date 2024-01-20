package maps;

import components.Vector2d;
import presenter.CellBox;
import worldElements.Animal;

import java.util.*;

public class MapCell {

    private final List<Animal> animals = new ArrayList<>();

    private final List<Animal> newMovedAnimals = new ArrayList<>();

    private final Vector2d cellPosition;

    public MapCell(Vector2d cellPosition) {
        this.cellPosition = cellPosition;
    }

    private final CellBox cellBox = new CellBox(this);


    public void placeAnimalOnCell(Animal animal) {
        animals.add(animal);
    }

    public List<Animal> removeDeads() {
        List<Animal> deadAnimals = getDeadAnimalsList();
        fixFamilyTree(deadAnimals);
        animals.removeAll(deadAnimals);
        return deadAnimals;
    }

    private static void fixFamilyTree(List<Animal> deadAnimals) {
        for (Animal deadAnimal: deadAnimals){
            deadAnimal.fixFamilyTree();
         }
    }

    private List<Animal> getDeadAnimalsList() {
        List<Animal> deadAnimals = new ArrayList<>();

        animals.forEach(animal -> {
            if (animal.isDead()) {
                deadAnimals.add(animal);
            }
        });
        return deadAnimals;
    }

    public void consumePlantOnCell() {
        animals.get(plantForAnimal()).eatPlant();
    }

    private int plantForAnimal() {
        return 0;
    }

    public void initializeReproduction() {
        Animal animal1 = animalForReproduction();
    }

    private Animal animalForReproduction() {
//        TODO
        return null;
    }

    public Animal takeAnimalFromCell() {
        return animals.remove(0);
    }

    public int animalNumber() {
        return animals.size();
    }

    public void addMovedAnimal(Animal animal) {
        newMovedAnimals.add(animal);
    }

    public void mergeAnimals() {
        animals.addAll(newMovedAnimals);
        newMovedAnimals.clear();
        sortAnimals();
    }

    public Vector2d getCellPosition() {
        return cellPosition;
    }

    public void survivedDay() {
        animals.forEach(Animal::skipDay);
    }

    public void sortAnimals() {
//        animals.sort();
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public CellBox getCellBox(){return cellBox;}
}
