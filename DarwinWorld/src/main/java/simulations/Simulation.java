package simulations;

import MapStatisticsAndInformations.AnimalInformation;
import maps.WorldMap;

public class Simulation implements Runnable {

    private final WorldMap map;

    private int speed = 500;

    private boolean stopped = false;

    public Simulation(WorldMap map, int startAnimalNumber, AnimalInformation animalInfo) {
        this.map = map;
        map.placeAnimals(startAnimalNumber, animalInfo);
    }

    @Override
    public void run() {
        while (map.areAnimals()) {
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
                System.out.println("Interrupted");;
            }
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void stopSimulation() {
        stopped = true;
    }

    public void continueSimulation() {
        stopped = false;
    }
}
