package presenter;

import components.MapStatistics;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.util.Arrays;

public class StatisticsPresenter {

    public Label actualDay;
    public Label animalNumber;
    public Label plantNumber;
    public Label freeCells;
    public ScrollPane mostPopularGenotype;
    public Label averageEnergy;
    public Label averageDeadLiveTime;
    public Label averageChildNumber;
    public Label averageAliveLiveTime;

    public void updateStats(MapStatistics stats) {
        actualDay.setText(getFormat(stats.getCurrentDay()));
        animalNumber.setText(getFormat(stats.getAllAliveAnimalNumber()));
        plantNumber.setText(getFormat(stats.getPlantNumber()));
        freeCells.setText(getFormat(stats.getFreeCellsNumber()));

        String mostPopularGenome = Arrays.toString(stats.getMostPopularGenome().getGenes());
        mostPopularGenotype.setContent(new Label(String.format("%s", mostPopularGenome)));

        averageEnergy.setText(getFormat(stats.getAvgEnergy()));
        averageDeadLiveTime.setText(getFormat(stats.getAvgDeadLiveTime()));
        averageAliveLiveTime.setText(getFormat(stats.getAvgCurrentLiveTime()));
        averageChildNumber.setText(getFormat(stats.getAvgChildNumber()));
    }

    private static String getFormat(int statistic) {
        return String.format("%d", statistic);
    }
}
