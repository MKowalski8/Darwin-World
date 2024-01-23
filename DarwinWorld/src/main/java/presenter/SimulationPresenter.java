package presenter;

import MapStatisticsAndInformations.Boundary;
import MapStatisticsAndInformations.GenomeSearcher;
import MapStatisticsAndInformations.MapStatistics;
import components.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import maps.MapCell;
import maps.WorldMap;
import simulations.MapChangeListener;
import simulations.Simulation;
import worldElements.Animal;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import javafx.stage.Screen;


public class SimulationPresenter implements MapChangeListener {

    private static final Background EMPTY_CELL_COLOR = new Background(new BackgroundFill(Color.rgb(127, 141, 121), CornerRadii.EMPTY, Insets.EMPTY));
    private static final Background GRASS_CELL_COLOR = new Background(new BackgroundFill(Color.rgb(38, 184, 2), CornerRadii.EMPTY, Insets.EMPTY));

    @FXML
    private Slider simulationSpeed;

    @FXML
    private Button genesButton;

    @FXML
    private Button plantsButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button continueButton;

    private WorldMap map;
    private Optional<Animal> followedAnimal = Optional.empty();

    private StatisticsPresenter statsBoxPresenter;
    private AnimalStatisticsPresenter followedBoxPresenter;

    @FXML
    private GridPane mapGrid = new GridPane();

    @FXML
    private Label worldType;

    @FXML
    private VBox statsBox;

    @FXML
    private VBox followedBox;

    private Simulation simulation;

    private int cellWidth;
    private int cellHeight;

    @FXML
    private void initialize() throws IOException {
        initializeStatBox();
        initializeFollowedBox();
    }

    private void initializeFollowedBox() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/followedAnimal.fxml"));
        VBox viewRoot = loader.load();
        this.followedBoxPresenter = loader.getController();
        followedBox.getChildren().add(viewRoot);
    }

    private void initializeStatBox() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/statistics.fxml"));
        VBox viewRoot = loader.load();
        this.statsBoxPresenter = loader.getController();
        statsBox.getChildren().add(viewRoot);
    }


    public void setWorldMap(WorldMap map, String mapType) {
        this.map = map;
        map.addObserver(this);
        worldType.setText(mapType);
        configureGridPane();
        Platform.runLater(() -> drawMap(map.getMapCellsList(), map.getPlants()));
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    private void configureGridPane() {
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        int mapGridWidth = (int) (screenHeight * 0.7);
        int mapGridHeight = (int) (screenHeight * 0.7);

        mapGrid.setMaxWidth(mapGridWidth);
        mapGrid.setMaxWidth(mapGridHeight);
        mapGrid.setBackground(EMPTY_CELL_COLOR);
        this.cellWidth = mapGridWidth / map.getBounds().getWidth();
        this.cellHeight = mapGridHeight / map.getBounds().getHeight();
    }

    private void drawMap(List<MapCell> mapCells, Set<Vector2d> plants) {
        clearGrid();

        Boundary bounds = map.getBounds();

        for (int row = 0; row < bounds.getWidth(); row++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }

        for (int column = 0; column < bounds.getHeight(); column++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
        }

        drawPlants(plants);
        drawAnimals(getCellBoxes(mapCells));
    }

    @FXML
    private void onClickStopSimulation() {
        simulation.stopSimulation();
        continueButton.setDisable(false);
        genesButton.setDisable(false);
        plantsButton.setDisable(false);
        stopButton.setDisable(true);
        changeClickAccessibility(getCellBoxes(map.getMapCellsList()));
    }

    @FXML
    private void onClickContinueSimulation() {
        simulation.continueSimulation();
        continueButton.setDisable(true);
        genesButton.setDisable(true);
        plantsButton.setDisable(true);
        stopButton.setDisable(false);
        changeClickAccessibility(getCellBoxes(map.getMapCellsList()));
        setButtonsToNormal();
    }

    private void changeClickAccessibility(List<CellBox> cellBoxes) {
        cellBoxes.forEach(cellBox -> {
            cellBox.setClickness(stopButton.isDisable(), this);
        });

    }

    private void drawPlants(Set<Vector2d> plants) {
        plants.forEach(plantPosition -> {
            Pane pane = new Pane();
            pane.setBackground(GRASS_CELL_COLOR);
            mapGrid.add(pane, plantPosition.getX(), plantPosition.getY());
        });
    }

    private void drawAnimals(List<CellBox> cellBoxes) {
        cellBoxes.forEach(cellBox -> {
            int row = cellBox.getPosition().getX();
            int column = cellBox.getPosition().getY();
            configureElementInGrid(cellBox);
            mapGrid.add(cellBox.getElement(), row, column);
        });

    }

    private void configureElementInGrid(CellBox cellBox) {
        GridPane.setHalignment(cellBox.getElement(), HPos.CENTER);
        cellBox.configureElement(cellHeight, cellWidth);
    }

    private List<CellBox> getCellBoxes(List<MapCell> mapCells) {
        return Stream.of(mapCells)
                .flatMap(Collection::stream)
                .map(MapCell::getCellBox)
                .toList();
    }

    @Override
    public void mapChanged(MapStatistics stats) {
        Platform.runLater(() -> {
            updateSimulation();
            statsBoxPresenter.updateStats(stats);
            followedBoxPresenter.updateFollowedAnimalStats(followedAnimal, stats.getCurrentDay());
            drawMap(map.getMapCellsList(), map.getPlants());
        });
    }

    private void updateSimulation() {
        simulation.setSpeed((int) simulationSpeed.getValue());
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void setFollowedAnimal(Animal followedAnimal) {
        this.followedAnimal = Optional.ofNullable(followedAnimal);
        Platform.runLater(() -> followedBoxPresenter.updateFollowedAnimalStats(this.followedAnimal, map.getMapStatistics().getCurrentDay()));
    }

    @FXML
    private void showMostPopularGenome() {
        if (genesButton.getText().equals("NAJPOPULARNIEJSZY GENOTYP")) {
            setButtonsToNormal();
            genesButton.setText("WSZYSTKIE GENOTYPY");

            Platform.runLater(() -> {
                List<MapCell> mapCells = GenomeSearcher.createMapCellListWithGenome(map.getMapStatistics().getMostPopularGenome(), map.getMapCellsList());
                changeClickAccessibility(getCellBoxes(mapCells));
                drawMap(mapCells, map.getPlants());
            });
        } else {
            setButtonsToNormal();
            Platform.runLater(() -> drawMap(map.getMapCellsList(), map.getPlants()));
        }
    }

    @FXML
    private void showBelovedPlantCells() {
        if (plantsButton.getText().equals("PREFEROWANE POLA DO WZROSTU")) {
            setButtonsToNormal();
            Platform.runLater(() -> drawMap(map.getMapCellsList(), map.getBounds().getJungleSet()));
            plantsButton.setText("WSZYSTKIE TRAWY");
        } else {
            Platform.runLater(() -> drawMap(map.getMapCellsList(), map.getPlants()));
            setButtonsToNormal();
        }
    }

    private void setButtonsToNormal() {
        plantsButton.setText("PREFEROWANE POLA DO WZROSTU");
        genesButton.setText("NAJPOPULARNIEJSZY GENOTYP");
    }

}

