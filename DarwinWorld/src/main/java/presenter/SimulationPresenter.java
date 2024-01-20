package presenter;

import components.Boundary;
import components.MapStatistics;
import components.Vector2d;
import javafx.application.Platform;
import javafx.fxml.FXML;
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class SimulationPresenter implements MapChangeListener {
    public Label animalNumber;
    public Label plantNumber;
    public Label freeCells;
    public Label mostPopularGenotype;
    public Label averageEnergy;
    public Label averageLiveTime;
    public Label averageChildNumber;
    public Label isFollowedAnimal;
    public Label followedGenome;
    public Label followedEnergy;
    public Label followedPlants;
    public Label followedChildren;
    public Label followedDescendants;
    public Label isFollowedAlive;
    public Label followedDays;
    public Slider simulationSpeed;
    public Button genesButton;
    public Button plantsButton;
    public Button stopButton;
    public Button continueButton;
    private WorldMap map;

    private static final Background EMPTY_CELL_COLOR = new Background(new BackgroundFill(Color.rgb(127, 141, 121), CornerRadii.EMPTY, Insets.EMPTY));
    private static final Background GRASS_CELL_COLOR = new Background(new BackgroundFill(Color.rgb(38, 184, 2), CornerRadii.EMPTY, Insets.EMPTY));

    @FXML
    GridPane mapGrid = new GridPane();

    @FXML
    Label worldType;

    Simulation simulation;

    private int cellWidth;
    private int cellHeight;

    public void setWorldMap(WorldMap map, String mapType) {
        this.map = map;
        map.addObserver(this);
        worldType.setText(mapType);
        configureGridPane();
        drawMap();
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    private void configureGridPane() {
        int mapGridWidth = 600;
        int mapGridHeight = 600;

        mapGrid.setMaxWidth(mapGridWidth);
        mapGrid.setMaxWidth(mapGridHeight);
        mapGrid.setBackground(EMPTY_CELL_COLOR);
        this.cellWidth = mapGridWidth / map.getBounds().getWidth();
        this.cellHeight = mapGridHeight / map.getBounds().getHeight();
    }

    public void drawMap() {
        clearGrid();

        Boundary bounds = map.getBounds();

        for (int row = 0; row < bounds.getWidth(); row++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }

        for (int column = 0; column < bounds.getHeight(); column++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
        }

        drawPlants();
        addAnimals();
    }

    @FXML
    public void onClickStopSimulation() {
        simulation.stopSimulation();
        continueButton.setDisable(false);
        genesButton.setDisable(false);
        plantsButton.setDisable(false);
        stopButton.setDisable(true);
    }
    @FXML
    public void onClickContinueSimulation(){
        simulation.continueSimulation();
        continueButton.setDisable(true);
        genesButton.setDisable(true);
        plantsButton.setDisable(true);
        stopButton.setDisable(false);
    }

    private void drawPlants() {
//        Na moje może tutaj być lista
        Map<Vector2d, Boolean> plants = map.getPlants();

        plants.keySet().forEach(plantPosition -> {
            Pane pane = new Pane();
            pane.setBackground(GRASS_CELL_COLOR);
            mapGrid.add(pane, plantPosition.getX(), plantPosition.getY());
        });

//        Taki test
        Pane pane = new Pane();
        pane.setBackground(GRASS_CELL_COLOR);
        mapGrid.add(pane, 0, 0);
    }

    private void addAnimals() {
        List<CellBox> cellBoxes = getCellBoxes();

        cellBoxes.forEach(cellBox -> {
            int row = cellBox.getPosition().getX();
            int column = cellBox.getPosition().getY();
            configureElementInGrid(cellBox);
            mapGrid.add(cellBox.getElement(), row, column);
        });

    }

    private void configureElementInGrid(CellBox cellBox) {
        GridPane.setHalignment(cellBox.getElement(), HPos.CENTER);
        cellBox.setAbleToClick(stopButton.isDisable());
        cellBox.configureElement(cellHeight, cellWidth);
    }

    private List<CellBox> getCellBoxes() {
        return Stream.of(map.getMapCellsList())
                .flatMap(Collection::stream)
                .map(MapCell::getCellBox)
                .toList();
    }

    @Override
    public void mapChanged(MapStatistics stats) {
        Platform.runLater(() -> {
            updateSimulation();
            updateStats(stats);
            drawMap();
        });
    }

    private void updateStats(MapStatistics stats) {
        animalNumber.setText(String.format("%d",stats.getAllAliveAnimalNumber()));
        plantNumber.setText(String.format("%d", stats.getPlantNumber()));
        freeCells.setText(String.format("%d",stats.getFreeCells()));
        mostPopularGenotype.setText(String.format("%d",stats.getMostPopularGenome()));
        averageEnergy.setText(String.format("%d",stats.getAvgEnergy()));
        averageLiveTime.setText(String.format("%d",stats.getAvgDeadLiveTime()));
        averageChildNumber.setText(String.format("%d",stats.getAvgChildNumber()));
    }

    private void updateSimulation() {
        simulation.setSpeed((int)simulationSpeed.getValue());
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}

