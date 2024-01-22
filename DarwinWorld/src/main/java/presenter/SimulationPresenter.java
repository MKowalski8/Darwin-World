package presenter;

import components.Boundary;
import components.GenomeSearcher;
import components.MapStatistics;
import components.Vector2d;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import maps.MapCell;
import maps.WorldMap;
import presenter.smallerElements.CellWithSpecificGenome;
import presenter.smallerElements.GenomeDrawing;
import simulations.MapChangeListener;
import simulations.Simulation;
import worldElements.Animal;

import java.util.*;
import java.util.stream.Stream;

import javafx.stage.Screen;


public class SimulationPresenter implements MapChangeListener {

    public Label actualDay;
    public Label animalNumber;
    public Label plantNumber;
    public Label freeCells;
    public ScrollPane mostPopularGenotype;
    public Label averageEnergy;
    public Label averageDeadLiveTime;
    public Label averageChildNumber;
    public Label isFollowedAnimal;
    public ScrollPane followedGenome;
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
    public Label averageAliveLiveTime;
    private WorldMap map;
    private Optional<Animal> followedAnimal = Optional.empty();

    private static final Background EMPTY_CELL_COLOR = new Background(new BackgroundFill(Color.rgb(127, 141, 121), CornerRadii.EMPTY, Insets.EMPTY));
    private static final Background GRASS_CELL_COLOR = new Background(new BackgroundFill(Color.rgb(38, 184, 2), CornerRadii.EMPTY, Insets.EMPTY));

    @FXML
    GridPane mapGrid = new GridPane();

    @FXML
    Label worldType;

    private Simulation simulation;

    private int cellWidth;
    private int cellHeight;

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

    public void drawMap(List<MapCell> mapCells, List<Vector2d> plants) {
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
    public void onClickStopSimulation() {
        simulation.stopSimulation();
        continueButton.setDisable(false);
        genesButton.setDisable(false);
        plantsButton.setDisable(false);
        stopButton.setDisable(true);
        changeClickAccessibility(getCellBoxes(map.getMapCellsList()));
    }

    @FXML
    public void onClickContinueSimulation() {
//        executorService.submit(simulation);
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

    private void drawPlants(List<Vector2d> plants) {
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
            updateStats(stats);
            updateFollowedAnimalStats();
            drawMap(map.getMapCellsList(), map.getPlants());
        });
    }

    private void updateStats(MapStatistics stats) {
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

        Platform.runLater(this::updateFollowedAnimalStats);
    }


    private void updateFollowedAnimalStats() {
        if (followedAnimal.isPresent()) {
            Animal animal = followedAnimal.get();
            if (!animal.isDead()) {
                String genome = Arrays.toString(animal.getGenome().getGenes());
                followedGenome.setContent(GenomeDrawing.drawGenome(genome, animal.getGeneIterator()));

                followedEnergy.setText(getFormat(animal.getEnergy()));
                followedPlants.setText(getFormat(animal.getNumberOfPlants()));
                followedChildren.setText(getFormat(animal.getNumberOfChildren()));
                followedDescendants.setText(getFormat(animal.getNumberOfUniqueDescendants()));

                setFollowedDays(animal);
            } else {
                setObituary();
            }

        }
    }

    private void setObituary() {
        if (!isFollowedAlive.getText().equals("Zwierze zmarlo: ")) {
            isFollowedAnimal.setText("SLEDZONY ZWIERZAK NIE ZYJE");
            isFollowedAlive.setText("Zwierze zmarlo: ");
            followedDays.setText(String.format("%d dnia", map.getMapStatistics().getCurrentDay()));
        }
    }

    private void setFollowedDays(Animal animal) {
        isFollowedAnimal.setText(String.format("SLEDZISZ ZWIERZAKA %s", animal.getId().toString()));
        isFollowedAlive.setText("Zwierze zyje: ");
        followedDays.setText(String.format("%d dni", animal.getLifeTime()));
    }

    private static String getFormat(int statistic) {
        return String.format("%d", statistic);
    }

    @FXML
    public void showMostPopularGenome() {
        if (genesButton.getText().equals("NAJPOPULARNIEJSZY GENOTYP")) {
            setButtonsToNormal();
            genesButton.setText("WSZYSTKIE GENOTYPY");

            Platform.runLater(() -> {
                List<MapCell> mapCells = map.getMapStatistics().getCellsContainingMostPopularGenome(map.getMapCellsList());
                changeClickAccessibility(getCellBoxes(mapCells));
                drawMap(mapCells, map.getPlants());
            });
        } else {
            setButtonsToNormal();
            Platform.runLater(() -> drawMap(map.getMapCellsList(), map.getPlants()));
        }
    }

    @FXML
    public void showBelovedPlantCells() {
        if (plantsButton.getText().equals("PREFEROWANE POLA DO WZROSTU")) {
            setButtonsToNormal();
            Platform.runLater(() -> drawMap(map.getMapCellsList(), jungleList()));
            plantsButton.setText("WSZYSTKIE TRAWY");
        } else {
            Platform.runLater(() -> drawMap(map.getMapCellsList(), map.getPlants()));
            setButtonsToNormal();
        }
    }

    public void setButtonsToNormal() {
        plantsButton.setText("PREFEROWANE POLA DO WZROSTU");
        genesButton.setText("NAJPOPULARNIEJSZY GENOTYP");
    }

    private List<Vector2d> jungleList() {
        List<Vector2d> jungleCells = new LinkedList<>();
        for (int i = 0; i < map.getBounds().getWidth(); i++) {
            for (int j = map.getBounds().getLowerJoungleBound(); j < map.getBounds().getUpperJoungleBound(); j++) {
                jungleCells.add(new Vector2d(i, j));
            }
        }

        return jungleCells;
    }
}

