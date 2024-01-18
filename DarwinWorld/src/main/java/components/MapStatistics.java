package components;

import worldElements.Animal;

import java.util.List;
import maps.MapCell;

public class MapStatistics {

    private int allAliveAnimalNumber=0;
    private int allDeadAnimalNumber=0;
    private int plantNumber=0;
    private int freeCells=0;
    private Genome mostPopularGenome=null;
    private int avgEnergy=0;
    private int avgDeadLiveTime=0;
    private int avgCurrentLiveTime=0;
    private int currentDay = 0;


    public void updateDeadLifetime(List<Animal> deadAnimals){
        if (!deadAnimals.isEmpty()){
            int newSum=0;

            for (Animal deadAnimal: deadAnimals) {
                newSum+=deadAnimal.getLifetime();
            }

            avgDeadLiveTime=((avgDeadLiveTime*allDeadAnimalNumber)+newSum)/(allDeadAnimalNumber+ deadAnimals.size());
            allDeadAnimalNumber+=deadAnimals.size();
            allAliveAnimalNumber-=deadAnimals.size();
        }
    }


    public void updateLiveStats(List<MapCell> mapCells){
        if (!mapCells.isEmpty()){
            //TODO mostPopularGenome();
            //TODO plantNumber();
            currentDay += 1;
            updatedAvgEnergyAndAnimalNumber(mapCells);
        }
    }

    private void updateAvgLifeTime(){

    }


    private void updatedAvgEnergyAndAnimalNumber(List<MapCell> mapCells) {
        int allEnergy = 0;
        allAliveAnimalNumber = 0;

        for (MapCell mapCell : mapCells){
            List<Animal> animalsOnCell = mapCell.getAnimals();
            allAliveAnimalNumber += animalsOnCell.size();
            allEnergy = getEnergyFromCell(animalsOnCell);
        }

        avgEnergy = allEnergy/allAliveAnimalNumber;
    }


    private int getEnergyFromCell(List<Animal> animalsOnCell) {
        int energyInCell = 0;

        for (Animal animal: animalsOnCell) {
            energyInCell += animal.getEnergy();
        }
        return energyInCell;
    }

}
