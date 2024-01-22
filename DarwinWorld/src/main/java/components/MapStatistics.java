package components;

import com.sun.scenario.animation.shared.AnimationAccessor;
import javafx.scene.control.Cell;
import javafx.util.Pair;
import worldElements.Animal;

import java.util.*;

import maps.MapCell;

public class MapStatistics {

    private int allAliveAnimalNumber = 0;
    private int allDeadAnimalNumber = 0;
    private int plantNumber = 0;
    private int freeCellsNumber = 0;
    private Genome mostPopularGenome = null;
    private int avgEnergy = 0;
    private int avgDeadLiveTime = 0;
    private int avgCurrentLiveTime = 0;
    private int currentDay = 0;
    private int avgChildNumber = 0;

    public void updateDeadLifetime(List<Animal> deadAnimals) {
        if (!deadAnimals.isEmpty()) {
            int newSum = 0;

            for (Animal deadAnimal : deadAnimals) {
                newSum += deadAnimal.getLifeTime();
            }

            avgDeadLiveTime = ((avgDeadLiveTime * allDeadAnimalNumber) + newSum) / (allDeadAnimalNumber + deadAnimals.size());
            allDeadAnimalNumber += deadAnimals.size();
        }
    }

    public void updateLiveStats(List<MapCell> mapCells, List<Vector2d> plants, Boundary bounds) {
        if (!mapCells.isEmpty()) {
            //prepare animals
            List<Animal> animals = createAnimalList(mapCells);

            //currentDay
            currentDay++;
            //allAliveAnimalNumber
            allAliveAnimalNumber = animals.size();
            //freeCells
            freeCellsNumber = (bounds.getHeight() * bounds.getWidth()) - mapCells.size();
            //avgEnergy
            calculateAvgEnergy(animals);
            //avgLifeTime
            calculateAvgLiveTime(animals);
            //avgChildNumber
            calculateAvgChildNumber(animals);
            //plants
            plantNumber = plants.size();
            //mostPopularGenome int and list

            mostPopularGenome=GenomeSearcher.calculateMostPopularGenome(mapCells);
        }
    }



    private void calculateAvgChildNumber(List<Animal> animals){
        int sum = 0;
        for (Animal animal : animals) {
            sum += animal.getNumberOfChildren();
        }
        avgChildNumber = sum / animals.size();
    }

    private void calculateAvgEnergy(List<Animal> animals) {
        int sum = 0;
        for (Animal animal : animals) {
            sum += animal.getEnergy();
        }
        avgEnergy = sum / animals.size();
    }

    private void calculateAvgLiveTime(List<Animal> animals) {
        int sum = 0;
        for (Animal animal : animals) {
            sum += animal.getLifeTime();
        }
        avgCurrentLiveTime = sum / animals.size();
    }

    private List<Animal> createAnimalList(List<MapCell> mapCells) {
        List<Animal> animals = new ArrayList<>();

        for (MapCell cell : mapCells) {
            animals.addAll(cell.getAnimals());
        }
        return animals;
    }

    public Genome getMostPopularGenome() {
        return mostPopularGenome;
    }

    public int getAllAliveAnimalNumber() {
        return allAliveAnimalNumber;

    }

    public int getAvgCurrentLiveTime() {
        return avgCurrentLiveTime;
    }

    public int getAvgDeadLiveTime() {
        return avgDeadLiveTime;
    }

    public int getAvgEnergy() {
        return avgEnergy;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public int getFreeCellsNumber() {
        return freeCellsNumber;
    }

    public int getPlantNumber() {
        return plantNumber;
    }

    public List<MapCell> getCellsContainingMostPopularGenome(List<MapCell> mapCells) {
        return GenomeSearcher.createMapCellListWithGenome(mostPopularGenome,mapCells);
    }

    public int getAvgChildNumber() {
        return avgChildNumber;
    }
}