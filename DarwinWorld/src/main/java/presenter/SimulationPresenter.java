package presenter;

import components.Boundary;
import components.MapStatistics;
import components.Vector2d;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import maps.MapCell;
import maps.WorldMap;
import simulations.MapChangeListener;

import java.util.List;
import java.util.Map;


public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;

    private static final Background EMPTY_CELL_COLOR = new Background(new BackgroundFill(Color.rgb(127, 141, 121), CornerRadii.EMPTY, Insets.EMPTY));
    private static final Background GRASS_CELL_COLOR = new Background(new BackgroundFill(Color.rgb(38, 184, 2), CornerRadii.EMPTY, Insets.EMPTY));

    @FXML
    GridPane mapGrid = new GridPane();

    @FXML
    Button stopButton;

    @FXML
    Button continueButton;

    @FXML
    Label infoLabel;

    MapStatistics stats;

    int cellWidth;
    int cellHeight;

    public void setWorldMap(WorldMap map, MapStatistics stats, String mapType) {
        this.map = map;
        this.stats = stats;
        map.addObserver(this);
        infoLabel.setText(mapType);
        configureGridPane();
        drawMap();
    }

    private void configureGridPane(){
        int mapGridWidth = 600;
        int mapGridHeight = 600;

        mapGrid.setMaxWidth(mapGridWidth);
        mapGrid.setMaxWidth(mapGridHeight);
        mapGrid.setBackground(EMPTY_CELL_COLOR);
        this.cellWidth = mapGridWidth/map.getBounds().getWidth();
        this.cellHeight = mapGridHeight/map.getBounds().getHeight();
    }

    public void drawMap() {
        clearGrid();

        Boundary bounds = map.getBounds();

        int cellWidth = 600/bounds.getWidth();


        for (int row = 0; row < bounds.getWidth(); row++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }

        for (int column = 0; column < bounds.getHeight(); column++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
        }

        drawPlants();
        addAnimals();

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

    private void addAnimals(){
        List<MapCell> mapCells = map.getMapCellsList();

        mapCells.forEach(mapCell -> {
                int row = mapCell.getCellPosition().getX();
                int column = mapCell.getCellPosition().getY();
                mapGrid.add(new Label("ANIMAL"), row, column);
        });

    }

    @Override
    public synchronized void mapChanged(WorldMap worldMap) {
        Platform.runLater(this::drawMap);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}

