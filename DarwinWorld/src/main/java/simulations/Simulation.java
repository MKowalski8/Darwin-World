package simulations;
import components.AnimalInformation;
import maps.WorldMap;
import worldElements.Animal;

public class Simulation implements Runnable{

    private final WorldMap map;

    public Simulation(WorldMap map, int startAnimalNumber, AnimalInformation animalInfo) {
        this.map = map;
//        umieszczanie zwierzakow na mapie
        map.placeAnimals(startAnimalNumber, animalInfo);
    }

    @Override
    public void run() {
        while(map.areAnimals()){
            map.cleanDeadAnimals();
            map.moveAnimals();
            map.consumePlants();
            map.reproduction();
            map.plantGrow();
            map.endDay();
        }

    }

}
