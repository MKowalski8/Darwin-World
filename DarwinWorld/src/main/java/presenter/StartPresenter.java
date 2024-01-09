package presenter;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;

import java.io.IOException;

public class StartPresenter {
    @FXML
    ComboBox mapVariant;

    @FXML
    ComboBox evolutionVariant;

    @FXML
    Spinner<Integer> mapHeight;

    @FXML
    Spinner<Integer> mapWidth;

    @FXML
    Spinner<Integer> plantNumber;

    @FXML
    Spinner<Integer> energyFromPlant;

    @FXML
    Spinner<Integer> startAnimalNumber;

    @FXML
    Spinner<Integer> startAnimalEnergy;

    @FXML
    Spinner<Integer> energyForReproduction;

    @FXML
    Spinner<Integer> maxMutationNumber;
    @FXML
    Spinner<Integer> minMutationNumber;

    @FXML
    Spinner<Integer> genomeLength;


    @FXML
    public void onSimulationStartClicked() throws IOException {

    }
}

