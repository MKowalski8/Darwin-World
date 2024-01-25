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

    @FXML
    private GridPane mapGrid = new GridPane();

    @FXML
    private Label worldType;

    @FXML
    private VBox statsBox;

    @FXML
    private VBox followedBox;

    private WorldMap map;
    private Optional<Animal> followedAnimal = Optional.empty();

    private StatisticsPresenter statsBoxPresenter;

    private AnimalStatisticsPresenter followedBoxPresenter;

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

        List<CellBox> cellBoxes = getCellBoxes(map.getMapCellsList());
        Set<Vector2d> plantsToDraw = map.getPlants();

        Platform.runLater(() -> drawMap(cellBoxes, plantsToDraw));
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

    private void drawMap(List<CellBox> cellBoxes, Set<Vector2d> plants) {
        clearGrid();

        Boundary bounds = map.getBounds();

        for (int row = 0; row < bounds.getWidth(); row++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }

        for (int column = 0; column < bounds.getHeight(); column++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
        }

        drawPlants(plants);
        drawAnimals(cellBoxes);
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
            mapGrid.add(cellBox.getElement(), row, column);
        });

    }

    private List<CellBox> getCellBoxes(List<MapCell> mapCells) {
        List<CellBox> cellBoxes = Stream.of(mapCells)
                .flatMap(Collection::stream)
                .map(MapCell::getCellBox)
                .toList();
        cellBoxes.forEach(this::configureCellBox);
        return cellBoxes;


    }

    private void configureCellBox(CellBox cellBox) {
        GridPane.setHalignment(cellBox.getElement(), HPos.CENTER);
        cellBox.configureElement(cellHeight, cellWidth);
    }

    @Override
    public void mapChanged(MapStatistics stats) {
        List<CellBox> cellBoxes = getCellBoxes(map.getMapCellsList());
        Set<Vector2d> plantsToDraw = map.getPlants();
        int currentDay = stats.getCurrentDay();


        Platform.runLater(() -> {
            updateSimulation();
            statsBoxPresenter.updateStats(stats, currentDay);
            followedBoxPresenter.updateFollowedAnimalStats(followedAnimal, currentDay);
            drawMap(cellBoxes, plantsToDraw);
        });
    }

    private void updateSimulation() {
        simulation.setSpeed((int) simulationSpeed.getValue());
    }

    public void setFollowedAnimal(Animal followedAnimal) {
        this.followedAnimal = Optional.ofNullable(followedAnimal);
        Platform.runLater(() -> followedBoxPresenter.updateFollowedAnimalStats(this.followedAnimal, map.getMapStatistics().getCurrentDay()));
    }

    @FXML
    private void showMostPopularGenome() {
        Set<Vector2d> plantsToDraw = map.getPlants();
        String actual = genesButton.getText();
        setButtonsToNormal();

        if (actual.equals("MOST POPULAR GENOTYPE")) {
            genesButton.setText("ALL GENOTYPES");
            List<CellBox> cellBoxes = getCellBoxes(GenomeSearcher.createMapCellListWithGenome(map.getMapStatistics().getMostPopularGenome(), map.getMapCellsList()));
            changeClickAccessibility(cellBoxes);
            Platform.runLater(() -> {
                drawMap(cellBoxes, plantsToDraw);
            });
        } else {
            List<CellBox> cellBoxes = getCellBoxes(map.getMapCellsList());
            Platform.runLater(() -> drawMap(cellBoxes, map.getPlants()));
        }
    }

    @FXML
    private void showBelovedPlantCells() {
        List<CellBox> cellBoxes = getCellBoxes(map.getMapCellsList());
        String actual = plantsButton.getText();
        setButtonsToNormal();

        if (actual.equals("PREFERRED PLANTS GROWTH AREAS")) {
            plantsButton.setText("ALL PLANTS");
            Set<Vector2d> plantsToDraw = map.getBounds().getJungleSet();
            Platform.runLater(() -> drawMap(cellBoxes, plantsToDraw));
        } else {
            Set<Vector2d> plantToDraw = map.getPlants();
            Platform.runLater(() -> drawMap(cellBoxes, plantToDraw));
        }
    }

    private void setButtonsToNormal() {
        plantsButton.setText("PREFERRED PLANTS GROWTH AREAS");
        genesButton.setText("MOST POPULAR GENOTYPE");
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}

