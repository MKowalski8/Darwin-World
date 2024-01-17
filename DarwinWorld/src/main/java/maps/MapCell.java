package maps;

import components.Vector2d;
import worldElements.Animal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MapCell {

    private final List<Animal> animals = new ArrayList<>();
    private final List<Animal> newMovedAnimals = new ArrayList<>();

    private final Vector2d cellPosition;

    public MapCell(Vector2d cellPosition){
        this.cellPosition = cellPosition;
    }


    public void placeAnimalOnCell(Animal animal){
        animals.add(animal);
    }

    public void removeDeads(){
        animals.forEach(animal -> {
//            if (animal.isDead()){
//                animals.remove(animal);
//            }
        });
    }

    public void consumePlantOnCell(){
        animals.get(plantForAnimal()).eatPlant();
    }

    private int plantForAnimal(){

        return 0;
    }

    public void initializeReproduction(){
        Animal animal1 = animalForReproduction();
    }

    private Animal animalForReproduction(){
//        TODO
        return null;
    }

    public Animal takeAnimalFromCell(){
        return animals.remove(0);
    }

    public int animalNumber(){
        return animals.size();
    }

    public void addMovedAnimal(Animal animal){
        newMovedAnimals.add(animal);
    }

    public void mergeAnimals(){
        animals.addAll(newMovedAnimals);
        newMovedAnimals.clear();
        sortAnimals();
    }

    public Vector2d getCellPosition() {
        return cellPosition;
    }

    public void survivedDay(){
        animals.forEach(Animal::skipDay);
    }

    public void sortAnimals(){
//        animals.sort();
    }

}
