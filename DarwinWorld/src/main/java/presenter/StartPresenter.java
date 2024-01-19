package presenter;

import components.AnimalInformation;
import components.Boundary;
import components.GenomeInformation;
import components.MapStatistics;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import maps.HellWorld;
import maps.RoundWorld;
import maps.WorldMap;
import simulations.MapChangeListener;
import simulations.Simulation;

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
    private Spinner<Integer> energyUsedByReproduction;

    @FXML
    private Spinner<Integer> energyUsedToSurviveNextDay;

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
        WorldMap map = configureWorldMap();
        simulationStart(map);
        presenter.setWorldMap(map, mapVariant.getValue());

        Stage stage = new Stage();
        configureStage(stage, viewRoot);
        stage.show();
    }

    private WorldMap configureWorldMap() {
        Boundary bounds = new Boundary(mapWidth.getValue(), mapHeight.getValue());

        return mapVariant.getValue().equals("Round World") ?
                new RoundWorld(bounds, plantNumber.getValue(),
                        plantGrowingDaily.getValue(), new MapStatistics())
                :
                new HellWorld(bounds, plantNumber.getValue(),
                        plantNumber.getValue(), new MapStatistics());
    }


    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation screen");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }


    public void simulationStart(WorldMap map) {
        GenomeInformation genomeInfo = getGenomeInformation();
        AnimalInformation animalInfo = new AnimalInformation(energyForReproduction.getValue(), energyUsedByReproduction.getValue(),
                startAnimalEnergy.getValue(),energyUsedToSurviveNextDay.getValue(), energyFromPlant.getValue(), genomeInfo);

        executorService.submit(new Simulation(map, startAnimalNumber.getValue(), animalInfo));
    }

    private GenomeInformation getGenomeInformation() {
        boolean slowEvolvingFlag = !evolutionVariant.getValue().equals("Normal Evolving Animal");

        return new GenomeInformation(maxMutationNumber.getValue(),minMutationNumber.getValue(), slowEvolvingFlag, genomeLength.getValue());
    }

    public void saveConfiguration() {
        Boundary bounds = new Boundary(mapWidth.getValue(), mapHeight.getValue());
        GenomeInformation genomeInfo = getGenomeInformation();
        AnimalInformation animalInfo = new AnimalInformation(energyForReproduction.getValue(), energyUsedByReproduction.getValue(),
                startAnimalEnergy.getValue(),energyUsedToSurviveNextDay.getValue(), energyFromPlant.getValue(), genomeInfo);

        FileConfiguration configuration = new FileConfiguration(animalInfo, bounds, mapVariant.getValue(), toSave.getValue(), plantNumber.getValue(),
                plantGrowingDaily.getValue(), startAnimalNumber.getValue());
        configuration.saveActualConfiguration();
    }

    public void loadConfiguration() {
        FileConfiguration configuration = new FileConfiguration(this);
        configuration.loadSavedConfiguration();
    }

    public void setToSave(String value) {
        toSave.setValue(value);
    }

    public void setMapVariant(String value) {
        mapVariant.setValue(value);
    }

    public void setEvolutionVariant(boolean slowEvolvingFlag) {
        if (slowEvolvingFlag){
            evolutionVariant.getSelectionModel().select(1);
        } else{
            evolutionVariant.getSelectionModel().select(0);
        }
    }

    public void setMapHeight(SpinnerValueFactory<Integer> mapHeight) {
        this.mapHeight.setValueFactory(mapHeight);
    }

    public void setMapWidth(SpinnerValueFactory<Integer> mapWidth) {
        this.mapWidth.setValueFactory(mapWidth);
    }

    public void setPlantNumber(SpinnerValueFactory<Integer> plantNumber) {
        this.plantNumber.setValueFactory(plantNumber);
    }

    public void setPlantGrowingDaily(SpinnerValueFactory<Integer> plantGrowingDaily) {
        this.plantGrowingDaily.setValueFactory(plantGrowingDaily);
    }

    public void setEnergyFromPlant(SpinnerValueFactory<Integer> energyFromPlant) {
        this.energyFromPlant.setValueFactory(energyFromPlant);
    }

    public void setStartAnimalNumber(SpinnerValueFactory<Integer> startAnimalNumber) {
        this.startAnimalNumber.setValueFactory(startAnimalNumber);
    }

    public void setStartAnimalEnergy(SpinnerValueFactory<Integer> startAnimalEnergy) {
        this.startAnimalEnergy.setValueFactory(startAnimalEnergy);
    }

    public void setEnergyForReproduction(SpinnerValueFactory<Integer> energyForReproduction) {
        this.energyForReproduction.setValueFactory(energyForReproduction);
    }

    public void setEnergyUsedByReproduction(SpinnerValueFactory<Integer> energyForReproduction) {
        this.energyUsedByReproduction.setValueFactory(energyForReproduction);
    }

    public void setEnergyUsedToSurviveNextDay(SpinnerValueFactory<Integer> energyForLivingDay) {
        this.energyUsedToSurviveNextDay.setValueFactory(energyForLivingDay);
    }

    public void setMaxMutationNumber(SpinnerValueFactory<Integer> maxMutationNumber) {
        this.maxMutationNumber.setValueFactory(maxMutationNumber);
    }

    public void setMinMutationNumber(SpinnerValueFactory<Integer> minMutationNumber) {
        this.minMutationNumber.setValueFactory(minMutationNumber);
    }

    public void setGenomeLength(SpinnerValueFactory<Integer> genomeLength) {
        this.genomeLength.setValueFactory(genomeLength);
    }
}

