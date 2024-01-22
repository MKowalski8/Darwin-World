package presenter.smallerElements;

import components.Genome;
import maps.MapCell;
import worldElements.Animal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CellWithSpecificGenome {

    public static List<MapCell> getCellsWithMostPopularGenome(Genome mostPopularGenome, List<MapCell> cells) {
        List<MapCell> cellList = new ArrayList<>();
        for (MapCell currentCell : cells) {
            ifAnimalOnCellHaveGenome(mostPopularGenome, currentCell, cellList);

        }
        System.out.println(cellList.size());
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
        for (Animal animal : currentCell.getAnimals()){
            if(animal.getGenome().equals(mostPopularGenome)) mapCellToAdd.placeAnimalOnCell(animal);
        }
    }

}
