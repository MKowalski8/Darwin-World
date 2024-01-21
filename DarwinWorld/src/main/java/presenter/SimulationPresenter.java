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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import maps.MapCell;
import maps.WorldMap;
import presenter.smallerElements.GenomeDrawing;
import simulations.MapChangeListener;
import simulations.Simulation;
import worldElements.Animal;
import java.util.*;
import java.util.stream.Stream;



public class SimulationPresenter implements MapChangeListener {

    public Label actualDay;
    public Label animalNumber;
    public Label plantNumber;
    public Label freeCells;
    public Label mostPopularGenotype;
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
        changeClickAccessibility();
    }

    @FXML
    public void onClickContinueSimulation() {
        simulation.continueSimulation();
        continueButton.setDisable(true);
        genesButton.setDisable(true);
        plantsButton.setDisable(true);
        stopButton.setDisable(false);
        changeClickAccessibility();
    }

    private void changeClickAccessibility() {
        getCellBoxes().forEach(cellBox -> {
            cellBox.setClickness(stopButton.isDisable(), this);
        });

    }

    private void drawPlants() {
        List<Vector2d> plants = map.getPlants();

        plants.forEach(plantPosition -> {
            Pane pane = new Pane();
            pane.setBackground(GRASS_CELL_COLOR);
            mapGrid.add(pane, plantPosition.getX(), plantPosition.getY());
        });
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
            updateFollowedAnimalStats();
            drawMap();
        });
    }

    private void updateStats(MapStatistics stats) {
        actualDay.setText(getFormat(stats.getCurrentDay()));
        animalNumber.setText(getFormat(stats.getAllAliveAnimalNumber()));
        plantNumber.setText(getFormat(stats.getPlantNumber()));
        freeCells.setText(getFormat(stats.getFreeCellsNumber()));
        String mostPopularGenome = Arrays.toString(stats.getMostPopularGenome().getGenes());
        mostPopularGenotype.setText(String.format("%s", mostPopularGenome));
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
        updateFollowedAnimalStats();
    }

    private void updateFollowedAnimalStats() {
        if (followedAnimal.isPresent()) {
            Animal animal = followedAnimal.get();
            String genome = Arrays.toString(animal.getGenome().getGenes());
            followedGenome = GenomeDrawing.drawGenome(genome, animal.getGeneIterator());
            followedEnergy.setText(getFormat(animal.getEnergy()));
            followedPlants.setText(getFormat(animal.getNumberOfPlants()));
            followedChildren.setText(getFormat(animal.getNumberOfChildren()));
            followedDescendants.setText(getFormat(animal.getNumberOfUniqueDescendants()));

            setFollowedDays(animal);
        }
    }

    private void setFollowedDays(Animal animal) {
        int day = Integer.parseInt(actualDay.getText());

        if (animal.isDead()) {
            isFollowedAlive.setText("Zwierze zyło: ");
            followedDays.setText(String.format("%d dni", day - animal.getLifeTime()));
        } else {
            isFollowedAlive.setText("Zwierze zyje: ");
            followedDays.setText(String.format("%d dni", animal.getLifeTime()));
        }
    }

    private static String getFormat(int statistic) {
        return String.format("%d", statistic);
    }
}

