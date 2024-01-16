package simulations;
import maps.WorldMap;

public class Simulation implements Runnable{

    private final WorldMap map;

    public Simulation(WorldMap map, int startAnimalNumber) {
        this.map = map;
//        umieszczanie zwierzakow na mapie
        map.placeAnimals(startAnimalNumber);
    }

    @Override
    public void run() {
        while(map.areAnimals()){
            map.cleanDeadAnimals();
            map.moveAnimals();
            map.consumePlants();
            map.reproduction();
            map.plantGrow();
        }

    }

}
