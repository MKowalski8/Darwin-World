package simulations;
import components.AnimalInformation;
import maps.WorldMap;
import worldElements.Animal;

public class Simulation implements Runnable{

    private final WorldMap map;

    private int speed = 500;

    private boolean stopped = false;

    public Simulation(WorldMap map, int startAnimalNumber, AnimalInformation animalInfo) {
        this.map = map;
//        umieszczanie zwierzakow na mapie
        map.placeAnimals(startAnimalNumber, animalInfo);
    }

    @Override
    public void run() {
        while(map.areAnimals()){

            if (!stopped){
                map.cleanDeadAnimals();
                map.moveAnimals();
                map.consumePlants();
                map.reproduction();
                map.plantGrow();
                map.endDay();

            }

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("end");
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void stopSimulation(){
        stopped = true;
    }

    public void continueSimulation(){
        stopped = false;
    }
}
