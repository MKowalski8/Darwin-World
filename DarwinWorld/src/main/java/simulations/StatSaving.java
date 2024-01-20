package simulations;

import components.MapStatistics;
import maps.WorldMap;
import simulations.MapChangeListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class StatSaving implements MapChangeListener {

    private final UUID id = UUID.randomUUID();
    File file = new File(String.format("simulation_%s.csv", id));

    public StatSaving(){
        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write("Simulation day,");
            writer.write("Animal Number,");
            writer.write("Plant Number,");
            writer.write("Free Cells,");
            writer.write("The Most Popular Genotype,");
            writer.write("Average Energy,");
            writer.write("Average LifeTime,");
            writer.write("Average Childes Number,");
            writer.write('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mapChanged(MapStatistics stats) {
        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write(getFormat(stats.getCurrentDay()));
            writer.write(getFormat(stats.getAllAliveAnimalNumber()));
            writer.write(getFormat(stats.getPlantNumber()));
            writer.write(getFormat(stats.getFreeCells()));
            writer.write(String.format("%s,", stats.getMostPopularGenome()));
            writer.write(getFormat(stats.getAvgEnergy()));
            writer.write(getFormat(stats.getAvgCurrentLiveTime()));
            writer.write(getFormat(stats.getAvgChildNumber()));
            writer.write('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFormat(int stat) {
        return String.format("%d,", stat);
    }
}
