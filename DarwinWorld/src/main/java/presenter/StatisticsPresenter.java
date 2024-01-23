package presenter;

import MapStatisticsAndInformations.MapStatistics;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.util.Arrays;

public class StatisticsPresenter {

    @FXML
    private Label actualDay;

    @FXML
    private Label animalNumber;

    @FXML
    private Label plantNumber;
    @FXML
    private Label freeCells;

    @FXML
    private ScrollPane mostPopularGenotype;

    @FXML
    private Label averageEnergy;

    @FXML
    private Label averageDeadLiveTime;

    @FXML
    private Label averageChildNumber;

    @FXML
    private Label averageAliveLiveTime;

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
