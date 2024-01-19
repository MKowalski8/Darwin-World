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
//        System.out.println(Thread.currentThread());
        while(map.areAnimals()){
            map.cleanDeadAnimals();
            map.moveAnimals();
            map.consumePlants();
            map.reproduction();
            map.plantGrow();
            map.endDay();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            System.out.println("endDay");
        }
        System.out.println("end");
    }

}
