import MapStatisticsAndInformations.AnimalInformation;
import MapStatisticsAndInformations.Boundary;
import MapStatisticsAndInformations.GenomeInformation;
import MapStatisticsAndInformations.MapStatistics;
import maps.RoundWorld;
import maps.WorldMap;
import simulations.Simulation;

public class World {
    public static void main(String[] args) {
        GenomeInformation genomeInfo = new GenomeInformation(10, 10, false, 10);
        AnimalInformation animalInfo = new AnimalInformation(10, 15,
                10, 5, 10, genomeInfo);

        WorldMap map = new RoundWorld(new Boundary(2, 2), 5, 5, new MapStatistics());
//        map.addObserver(new StatSaving());
        Simulation simulation = new Simulation(map, 5, animalInfo);
        simulation.run();
    }
}