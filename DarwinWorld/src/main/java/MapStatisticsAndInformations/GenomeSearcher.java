package MapStatisticsAndInformations;

import components.Genome;
import maps.MapCell;
import worldElements.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenomeSearcher {
    public static Genome calculateMostPopularGenome(List<MapCell> cells) {
        Map<Genome, Integer> genesNumberMap = new HashMap<>();
        prepareGenomeStats(genesNumberMap, cells);
        int numberAnimalsWithMostPopularGenome = maxIntegerFromHashMap(genesNumberMap);
        return findMostPopularGenome(genesNumberMap, numberAnimalsWithMostPopularGenome);
    }

    private static void prepareGenomeStats(Map<Genome, Integer> genesNumberMap, List<MapCell> cells) {

        for (MapCell cell : cells) {
            for (Animal animal : cell.getAnimals()) {
                actualizeGenesNumberMap(genesNumberMap, animal);
            }
        }
    }

    private static void actualizeGenesNumberMap(Map<Genome, Integer> genesNumberMap, Animal animal) {
        if (genesNumberMap.containsKey(animal.getGenome())) {
            genesNumberMap.put(animal.getGenome(), genesNumberMap.get(animal.getGenome()) + 1);
        } else {
            genesNumberMap.put(animal.getGenome(), Integer.valueOf(0));
        }
    }

    private static Genome findMostPopularGenome(Map<Genome, Integer> genesNumberMap,
                                                int numOfAnimalsWithMostPopularGenom) {
        Genome mostPopularGenomes = null;
        for (Genome currentGenome : genesNumberMap.keySet()) {
            if (genesNumberMap.get(currentGenome).equals(numOfAnimalsWithMostPopularGenom)) {
                mostPopularGenomes = currentGenome;
                break;
            }
        }
        return mostPopularGenomes;
    }

    private static Integer maxIntegerFromHashMap(Map<Genome, Integer> genesNumberMap) {
        Integer maxVal = Integer.MIN_VALUE;
        for (Integer currentVal : genesNumberMap.values()) {
            if (currentVal > maxVal) {
                maxVal = currentVal;
            }
        }
        return maxVal;
    }

    public static List<MapCell> createMapCellListWithGenome(Genome genome, List<MapCell> allCells) {
        List<MapCell> cellList = new ArrayList<>();
        for (MapCell currentCell : allCells) {
            ifAnimalOnCellHaveGenome(genome, currentCell, cellList);
        }
        return cellList;
    }

    private static void ifAnimalOnCellHaveGenome(Genome genome, MapCell currentCell, List<MapCell> cellList) {
        for (Animal animal : currentCell.getAnimals()) {
            MapCell mapCell = new MapCell(currentCell.getCellPosition());
            if (animal.getGenome().equals(genome)) {
                cellList.add(mapCell);
                addAnimalsOnCellsWithGenome(genome, mapCell, currentCell);
                break;
            }
        }
    }

    private static void addAnimalsOnCellsWithGenome(Genome mostPopularGenome, MapCell mapCellToAdd, MapCell currentCell) {
        for (Animal animal : currentCell.getAnimals()) {
            if (animal.getGenome().equals(mostPopularGenome)) mapCellToAdd.placeAnimalOnCell(animal);
        }
    }
}
