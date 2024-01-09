package presenter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import maps.HellWorld;
import maps.RoundWorld;
import maps.WorldMap;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartPresenter {

    @FXML
    ComboBox<String> toSave;

    @FXML
    ComboBox<String> mapVariant;

    @FXML
    ComboBox<String> evolutionVariant;

    @FXML
    Spinner<Integer> mapHeight;

    @FXML
    Spinner<Integer> mapWidth;

    @FXML
    Spinner<Integer> plantNumber;

    @FXML
    Spinner<Integer> plantGrowingDaily;

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



    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @FXML
    public void initialize() {
        toSave.getItems().addAll("Yes", "No");
        mapVariant.getItems().addAll("Round World", "Hell World");
        evolutionVariant.getItems().addAll("normal evolving animal", "slow evolving animal");
    }

    @FXML
    public void onSimulationStartClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/simulation.fxml"));

        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        presenter.setWorldMap(configureWorldMap(), mapVariant.getValue());

        Stage stage = new Stage();
        configureStage(stage, viewRoot);
        stage.show();
    }

    private WorldMap configureWorldMap() {
        return mapVariant.equals("Round World") ?
                new RoundWorld(mapWidth.getValue(), mapHeight.getValue(), plantNumber.getValue(), energyFromPlant.getValue(),
                    plantGrowingDaily.getValue(), startAnimalNumber.getValue())
                :
                new HellWorld(mapWidth.getValue(), mapHeight.getValue(), plantNumber.getValue(), energyFromPlant.getValue(),
                    plantGrowingDaily.getValue(), startAnimalNumber.getValue());
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation screen");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void simulationStart(String[] args, WorldMap map) {

    }
}

