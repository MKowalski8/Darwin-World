package components;

import worldElements.Animal;

import java.util.*;

import maps.MapCell;

public class MapStatistics {

    private int allAliveAnimalNumber = 0;
    private int allDeadAnimalNumber = 0;
    private int plantNumber = 0;
    private int freeCellsNumber = 0;
    private Genome mostPopularGenome = null;
    private List<MapCell> cellsContainingMostPopularGenome;
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
            //        allDeadAnimalNumber=0;
            allDeadAnimalNumber += deadAnimals.size();
        }
    }

    public void updateLiveStats(List<MapCell> mapCells, List<Vector2d> plants, Boundary bounds) {
        if (!mapCells.isEmpty()){
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
            //plants
            plantNumber = plants.size();
            //mostPopularGenome int and list
            calculateMostPopularGenome(mapCells);
        }
    }


    private void calculateMostPopularGenome(List<MapCell> cells) {
        Map<Genome, Integer> genesNumberMap = new HashMap<>();
        prepareGenomeStats(genesNumberMap, cells);
        int numberAnimalsWithMostPopularGenome = maxIntegerFromHashMap(genesNumberMap);
        calculateMostPopularGenome(genesNumberMap, numberAnimalsWithMostPopularGenome);
        cellsContainingMostPopularGenome = createMapCellListWithGenome(mostPopularGenome, cells);

    }

    private void prepareGenomeStats(Map<Genome, Integer> genesNumberMap, List<MapCell> cells) {

        for (MapCell cell : cells) {
            for (Animal animal : cell.getAnimals()) {
                if (genesNumberMap.containsKey(animal.getGenome())) {
                    genesNumberMap.put(animal.getGenome(), genesNumberMap.get(animal.getGenome()) + 1);
                } else {
                    genesNumberMap.put(animal.getGenome(), Integer.valueOf(0));
                }
            }
        }
    }

    private void calculateMostPopularGenome(Map<Genome, Integer> genesNumberMap, int numOfAnimalsWithMostPopularGenom) {
        for (Genome currentGenome : genesNumberMap.keySet()) {
            if (genesNumberMap.get(currentGenome).equals(numOfAnimalsWithMostPopularGenom)) {
                mostPopularGenome = currentGenome;
                break;
            }
        }
    }

    private Integer maxIntegerFromHashMap(Map<Genome, Integer> genesNumberMap) {
        Integer maxVal = Integer.MIN_VALUE;
        for (Integer currentVal : genesNumberMap.values()) {
            if (currentVal > maxVal) {
                maxVal = currentVal;
            }
        }
        return maxVal;
    }

    private List<MapCell> createMapCellListWithGenome(Genome genome, List<MapCell> allCells) {
        List<MapCell> cellList = new ArrayList<>();
        for (MapCell currentCell : allCells) {
            ifAnimalOnCellHaveGenome(genome, currentCell, cellList);
        }
        return cellList;
    }

    private static void ifAnimalOnCellHaveGenome(Genome genome, MapCell currentCell, List<MapCell> cellList) {
        for (Animal animal : currentCell.getAnimals()) {
            if (animal.getGenome().equals(genome)) {
//                if (!cellList.contains(currentCell)) {
                cellList.add(currentCell);
//                }
//                Popraw mnie, jeżeli źle rozumiem tę metodę, ale tu chyba można to breakować
//                i ten if chyba nie był potrzebny, bo jedziemy zawsze po roznych cellach
                break;
            }
        }
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

    public List<MapCell> getCellsContainingMostPopularGenome() {
        return cellsContainingMostPopularGenome;
    }

    public int getAvgChildNumber(){
        return avgChildNumber;
    }
}