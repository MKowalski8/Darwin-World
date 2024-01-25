package presenter;

import MapStatisticsAndInformations.AnimalInformation;
import MapStatisticsAndInformations.Boundary;
import MapStatisticsAndInformations.GenomeInformation;
import MapStatisticsAndInformations.MapStatistics;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import maps.HellWorld;
import maps.RoundWorld;
import maps.WorldMap;
import simulations.Simulation;
import simulations.StatSaving;

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
    private Spinner<Integer> startPlantNumber;

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
        simulationPresenterConfiguration(presenter);
        Stage stage = new Stage();
        configureStage(stage, viewRoot);
        stage.show();
    }

    private void simulationPresenterConfiguration(SimulationPresenter presenter) {
        WorldMap map = configureWorldMap();
        setToSaveStats(map);
        presenter.setWorldMap(map, mapVariant.getValue());
        presenter.setSimulation(simulationConf(map));
    }

    private void setToSaveStats(WorldMap map) {
        if (toSave.getValue().equals("Yes")) {
            map.addObserver(new StatSaving());
        }
    }

    private WorldMap configureWorldMap() {
        Boundary bounds = new Boundary(mapWidth.getValue(), mapHeight.getValue());

        return mapVariant.getValue().equals("Round World") ?
                new RoundWorld(bounds, startPlantNumber.getValue(),
                        plantGrowingDaily.getValue(), new MapStatistics())
                :
                new HellWorld(bounds, startPlantNumber.getValue(),
                        startPlantNumber.getValue(), new MapStatistics());
    }


    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation screen");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }


    public Simulation simulationConf(WorldMap map) {
        GenomeInformation genomeInfo = getGenomeInformation();
        AnimalInformation animalInfo = new AnimalInformation(energyForReproduction.getValue(), energyUsedByReproduction.getValue(),
                startAnimalEnergy.getValue(), energyUsedToSurviveNextDay.getValue(), energyFromPlant.getValue(), genomeInfo);
        Simulation simulation = new Simulation(map, startAnimalNumber.getValue(), animalInfo);
        executorService.submit(simulation);
        return simulation;
    }


    @FXML
    public void saveConfiguration() {
        Boundary bounds = new Boundary(mapWidth.getValue(), mapHeight.getValue());
        GenomeInformation genomeInfo = getGenomeInformation();
        AnimalInformation animalInfo = new AnimalInformation(energyForReproduction.getValue(), energyUsedByReproduction.getValue(),
                startAnimalEnergy.getValue(), energyUsedToSurviveNextDay.getValue(), energyFromPlant.getValue(), genomeInfo);

        FileConfiguration configuration = new FileConfiguration(animalInfo, bounds, mapVariant.getValue(), toSave.getValue(), startPlantNumber.getValue(),
                plantGrowingDaily.getValue(), startAnimalNumber.getValue());
        configuration.saveActualConfiguration();

    }

    private GenomeInformation getGenomeInformation() {
        boolean slowEvolvingFlag = !evolutionVariant.getValue().equals("Normal Evolving Animal");

        return new GenomeInformation(minMutationNumber.getValue(), maxMutationNumber.getValue(), slowEvolvingFlag, genomeLength.getValue());
    }

    @FXML
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
        if (slowEvolvingFlag) {
            evolutionVariant.getSelectionModel().select(1);
        } else {
            evolutionVariant.getSelectionModel().select(0);
        }
    }

    public void setMapHeight(int mapHeightValue) {
        this.mapHeight.getValueFactory().setValue(mapHeightValue);
    }

    public void setMapWidth(int mapWidthValue) {
        this.mapWidth.getValueFactory().setValue(mapWidthValue);
    }

    public void setStartPlantNumber(int plantNumber) {
        startPlantNumber.getValueFactory().setValue(plantNumber);
    }

    public void setPlantGrowingDaily(int plantGrowingDailyValue) {
        this.plantGrowingDaily.getValueFactory().setValue(plantGrowingDailyValue);
    }

    public void setEnergyFromPlant(int energyFromPlantValue) {
        this.energyFromPlant.getValueFactory().setValue(energyFromPlantValue);
    }

    public void setStartAnimalNumber(int startAnimalNumberValue) {
        this.startAnimalNumber.getValueFactory().setValue(startAnimalNumberValue);
    }

    public void setStartAnimalEnergy(int startAnimalEnergyValue) {
        this.startAnimalEnergy.getValueFactory().setValue(startAnimalEnergyValue);
    }

    public void setEnergyForReproduction(int energyForReproductionValue) {
        this.energyForReproduction.getValueFactory().setValue(energyForReproductionValue);
    }

    public void setEnergyUsedByReproduction(int energyUsedByReproductionValue) {
        this.energyUsedByReproduction.getValueFactory().setValue(energyUsedByReproductionValue);
    }

    public void setEnergyUsedToSurviveNextDay(int energyUsedToSurviveNextDayValue) {
        this.energyUsedToSurviveNextDay.getValueFactory().setValue(energyUsedToSurviveNextDayValue);
    }

    public void setMaxMutationNumber(int maxMutationNumberValue) {
        this.maxMutationNumber.getValueFactory().setValue(maxMutationNumberValue);
    }

    public void setMinMutationNumber(int minMutationNumberValue) {
        this.minMutationNumber.getValueFactory().setValue(minMutationNumberValue);
    }

    public void setGenomeLength(int genomeLengthValue) {
        this.genomeLength.getValueFactory().setValue(genomeLengthValue);
    }

}

