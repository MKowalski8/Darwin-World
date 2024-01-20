package components;

import com.sun.scenario.animation.shared.AnimationAccessor;
import javafx.scene.control.Cell;
import javafx.util.Pair;
import worldElements.Animal;

import java.util.*;

import maps.MapCell;

public class MapStatistics {

    Boundary bounds;
    private int allAliveAnimalNumber=0;
    private int allDeadAnimalNumber=0;
    private int plantNumber=0;
    private int freeCellsNumber=0;
    private Genome mostPopularGenome=null;
    private List<MapCell> cellsContainingMostPopularGene;
    private int avgEnergy=0;
    private int avgDeadLiveTime=0;
    private int avgCurrentLiveTime=0;
    private int currentDay = 0;

//    public MapStatistics(Boundary bounds){
//        this.bounds=bounds;
//    }

    public void updateDeadLifetime(List<Animal> deadAnimals){
        if (!deadAnimals.isEmpty()){
            int newSum=0;

            for (Animal deadAnimal: deadAnimals) {
                newSum+=deadAnimal.getLifetime();
            }

            avgDeadLiveTime=((avgDeadLiveTime*allDeadAnimalNumber)+newSum)/(allDeadAnimalNumber+ deadAnimals.size());
            //        allDeadAnimalNumber=0;
            allDeadAnimalNumber+=deadAnimals.size();
        }
    }

    public void updateLiveStats(List<MapCell> mapCells,ArrayList<Vector2d> plants){

        //prepare animals
        ArrayList<Animal> animals=createAnimalList(mapCells);

        //currentDay
        currentDay++;
        //allAliveAnimalNumber
        allAliveAnimalNumber=animals.size();
        //freeCells
        freeCellsNumber=(bounds.getHeight()* bounds.getWidth())-mapCells.size();
        //avgEnergy
        calculateAvgEnergy(animals);
        //avglifetime
        calculateAvgLiveTime(animals);
        //plants
        plantNumber=plants.size();
        //mostPopulatGenome int and list
        calculateMostPopulatGenome(mapCells);
    }


    private void calculateMostPopulatGenome(List<MapCell> cells){
        HashMap<Genome,Integer> genesNumberMap = new HashMap<>();
        prepareGenomeStats(genesNumberMap,cells);
        int numOfAnimalsWithMostPopularGenom=maxIntigerFromHashMap(genesNumberMap);
        calculateMostPopularGenome(genesNumberMap,numOfAnimalsWithMostPopularGenom);
        cellsContainingMostPopularGene=createMapCellListWithGenome(mostPopularGenome,cells);

    }

    private void prepareGenomeStats(HashMap<Genome, Integer> genesNumberMap,List<MapCell> cells) {

        for(MapCell cell:cells){
            for (Animal animal:cell.getAnimals()){
                if (genesNumberMap.containsKey(animal.getGenome())){
                genesNumberMap.put(animal.getGenome(), genesNumberMap.get(animal.getGenome())+1);
                }
                else {
                    genesNumberMap.put(animal.getGenome(),Integer.valueOf(0));
                }
            }
        }
    }

    private void calculateMostPopularGenome(HashMap<Genome,Integer> genesNumberMap,int numOfAnimalsWithMostPopularGenom){
        for (Genome currentGenome:genesNumberMap.keySet()){
            if (genesNumberMap.get(currentGenome).equals(numOfAnimalsWithMostPopularGenom))
            {
                mostPopularGenome=currentGenome;
                break;
            }
        }
    }
    private Integer maxIntigerFromHashMap(HashMap<Genome,Integer> genesNumberMap){
        Integer maxVal= Integer.MIN_VALUE;
        for(Integer currentVal:genesNumberMap.values()){
            if(currentVal>maxVal){
                maxVal=currentVal;
            }
        }
        return maxVal;
    }

    private List<MapCell> createMapCellListWithGenome(Genome genome,List<MapCell> allCells){
        List<MapCell> cellList=new ArrayList<>();
        for(MapCell currentCell:allCells){
            for (Animal animal:currentCell.getAnimals()){
                if (animal.getGenome().equals(genome)){
                    if (!cellList.contains(currentCell)){
                        cellList.add(currentCell);
                    }
                }
            }
        }
        return cellList;
    }

    private void calculateAvgEnergy(ArrayList<Animal> animals){
        int sum=0;
        for(Animal animal : animals){
            sum+=animal.getEnergy();
        }
        avgEnergy=sum/animals.size();
    }
    private void calculateAvgLiveTime(ArrayList<Animal> animals){
        int sum=0;
        for(Animal animal : animals){
            sum+=animal.getLifetime();
        }
        avgEnergy=sum/animals.size();
    }

    private ArrayList<Animal> createAnimalList(List<MapCell> mapCells){
        ArrayList<Animal> animals =new ArrayList<>();

        for(MapCell cell : mapCells){
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

    public int getAllDeadAnimalNumber() {
        return allDeadAnimalNumber;
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

    public List<MapCell> getCellsContainingMostPopularGene() {
        return cellsContainingMostPopularGene;
    }

}
