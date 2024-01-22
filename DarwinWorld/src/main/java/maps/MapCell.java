package maps;

import components.Vector2d;
import presenter.CellBox;
import worldElements.Animal;

import java.util.*;
import java.util.stream.Stream;

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
        sortAnimals();
    }

    public List<Animal> removeDeads() {
        List<Animal> deadAnimals = getDeadAnimalsList();
        fixFamilyTree(deadAnimals);
        animals.removeAll(deadAnimals);
        return deadAnimals;
    }

    private static void fixFamilyTree(List<Animal> deadAnimals) {
        for (Animal deadAnimal : deadAnimals) {
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
        animals.get(0).eatPlant();
    }

    public void initializeReproduction() {
        if (animals.size() >= 2) {
            Animal animal1 = animals.get(0);
            Animal animal2 = animals.get(1);
            if (animal1.canReproduce() && animal2.canReproduce())
                animals.add(animal1.reproduce(animal2));
        }
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
        animals.stream()
                .sorted(Comparator.comparingInt(Animal::getEnergy)
                        .thenComparingInt(Animal::getLifeTime)
                        .thenComparingInt(Animal::getNumberOfChildren)
                        .thenComparing(animal -> animal.getId().toString()));
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public CellBox getCellBox() {
        return cellBox;
    }
}
