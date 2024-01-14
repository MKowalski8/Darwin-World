package presenter;

import components.Boundary;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private ComboBox<String> toSave;

    @FXML
    private ComboBox<String> mapVariant;

    @FXML
    private ComboBox<String> evolutionVariant;

    @FXML
    private Spinner<Integer> mapHeight;

    @FXML
    private Spinner<Integer> mapWidth;

    @FXML
    private Spinner<Integer> plantNumber;

    @FXML
    private Spinner<Integer> plantGrowingDaily;

    @FXML
    private Spinner<Integer> energyFromPlant;

    @FXML
    private Spinner<Integer> startAnimalNumber;

    @FXML
    private Spinner<Integer> startAnimalEnergy;

    @FXML
    private Spinner<Integer> energyForReproduction;

    @FXML
    private Spinner<Integer> maxMutationNumber;

    @FXML
    private Spinner<Integer> minMutationNumber;

    @FXML
    private Spinner<Integer> genomeLength;

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @FXML
    public void initialize() {
        toSave.getItems().addAll("Yes", "No");
        toSave.setValue("Yes");
        mapVariant.getItems().addAll("Round World", "Hell World");
        mapVariant.setValue("Round World");
        evolutionVariant.getItems().addAll("Normal Evolving Animal", "Slow Evolving Animal");
        evolutionVariant.setValue("Normal Evolving Animal");
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
        Boundary bounds = new Boundary(getMapWidth(), getMapHeight());

        return getMapVariant().equals("Round World") ?
                new RoundWorld(bounds, getPlantNumber(), getEnergyFromPlant(),
                        getPlantGrowingDaily(), getStartAnimalNumber())
                :
                new HellWorld(bounds, getPlantNumber(), getEnergyFromPlant(),
                        getPlantGrowingDaily(), getStartAnimalNumber());
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


    public void saveConfiguration() {
        FileConfiguration configuration = new FileConfiguration(this);
        configuration.saveActualConfiguration();
    }

    public void loadConfiguration() {
        FileConfiguration configuration = new FileConfiguration(this);
        configuration.loadSavedConfiguration();
    }

    public String getToSave() {
        return toSave.getValue();
    }

    public void setToSave(String value) {
        toSave.setValue(value);
    }

    public String getMapVariant() {
        return mapVariant.getValue();
    }

    public void setMapVariant(String value) {
        mapVariant.setValue(value);
    }

    public String getEvolutionVariant() {
        return evolutionVariant.getValue();
    }

    public void setEvolutionVariant(String value) {
        evolutionVariant.setValue(value);
    }

    public int getMapHeight() {
        return mapHeight.getValue();
    }

    public void setMapHeight(SpinnerValueFactory<Integer> mapHeight) {
        this.mapHeight.setValueFactory(mapHeight);
    }

    public int getMapWidth() {
        return mapWidth.getValue();
    }

    public void setMapWidth(SpinnerValueFactory<Integer> mapWidth) {
        this.mapWidth.setValueFactory(mapWidth);
    }

    public int getPlantNumber() {
        return plantNumber.getValue();
    }

    public void setPlantNumber(SpinnerValueFactory<Integer> plantNumber) {
        this.plantNumber.setValueFactory(plantNumber);
    }

    public int getPlantGrowingDaily() {
        return plantGrowingDaily.getValue();
    }

    public void setPlantGrowingDaily(SpinnerValueFactory<Integer> plantGrowingDaily) {
        this.plantGrowingDaily.setValueFactory(plantGrowingDaily);
    }

    public int getEnergyFromPlant() {
        return energyFromPlant.getValue();
    }

    public void setEnergyFromPlant(SpinnerValueFactory<Integer> energyFromPlant) {
        this.energyFromPlant.setValueFactory(energyFromPlant);
    }

    public int getStartAnimalNumber() {
        return startAnimalNumber.getValue();
    }

    public void setStartAnimalNumber(SpinnerValueFactory<Integer> startAnimalNumber) {
        this.startAnimalNumber.setValueFactory(startAnimalNumber);
    }

    public int getStartAnimalEnergy() {
        return startAnimalEnergy.getValue();
    }

    public void setStartAnimalEnergy(SpinnerValueFactory<Integer> startAnimalEnergy) {
        this.startAnimalEnergy.setValueFactory(startAnimalEnergy);
    }

    public int getEnergyForReproduction() {
        return energyForReproduction.getValue();
    }

    public void setEnergyForReproduction(SpinnerValueFactory<Integer> energyForReproduction) {
        this.energyForReproduction.setValueFactory(energyForReproduction);
    }

    public int getMaxMutationNumber() {
        return maxMutationNumber.getValue();
    }

    public void setMaxMutationNumber(SpinnerValueFactory<Integer> maxMutationNumber) {
        this.maxMutationNumber.setValueFactory(maxMutationNumber);
    }

    public int getMinMutationNumber() {
        return minMutationNumber.getValue();
    }

    public void setMinMutationNumber(SpinnerValueFactory<Integer> minMutationNumber) {
        this.minMutationNumber.setValueFactory(minMutationNumber);
    }

    public int getGenomeLength() {
        return genomeLength.getValue();
    }

    public void setGenomeLength(SpinnerValueFactory<Integer> genomeLength) {
        this.genomeLength.setValueFactory(genomeLength);
    }
}

