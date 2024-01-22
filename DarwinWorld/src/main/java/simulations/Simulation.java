package simulations;

import MapStatisticsAndInformations.AnimalInformation;
import maps.WorldMap;

public class Simulation implements Runnable {

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
        while (map.areAnimals()) {
            if (!stopped) {
//                System.out.println(Thread.currentThread());
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
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void stopSimulation() {
        stopped = true;
//        Thread.currentThread().interrupt();
    }

    public void continueSimulation() {
        stopped = false;
    }
}
