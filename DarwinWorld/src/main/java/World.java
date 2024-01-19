import components.AnimalInformation;
import components.Boundary;
import components.GenomeInformation;
import components.MapStatistics;
import maps.RoundWorld;
import maps.WorldMap;
import simulations.Simulation;

public class World {
    public static void main(String[] args) {
        GenomeInformation genomeInfo =  new GenomeInformation(10,10, false, 10);
        AnimalInformation animalInfo = new AnimalInformation(10, 10,
                1,100, 10, genomeInfo);

        WorldMap map = new RoundWorld(new Boundary(5,5), 5, 5, new MapStatistics());

        Simulation simulation = new Simulation(map, 1, animalInfo);
        simulation.run();
    }
}