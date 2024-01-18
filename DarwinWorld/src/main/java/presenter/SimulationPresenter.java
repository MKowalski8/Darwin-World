package presenter;

import components.Boundary;
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


    public void setWorldMap(WorldMap map, String mapType) {
        this.map = map;
        map.addObserver(this);
        infoLabel.setText(mapType);
        drawMap(map);
    }

    public void drawMap(WorldMap worldMap) {
        clearGrid();

        Boundary bounds = map.getBounds();
        int cellWidth = 600/bounds.getWidth();
        int cellHeight = 600/bounds.getHeight();

        for (int row = 0; row <= bounds.getWidth()-1; row++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }

        for (int column = 0; column <= bounds.getHeight()-1; column++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
        }

        drawPlants();
        addAnimals();

    }

    private void drawPlants() {
        Map<Vector2d, Boolean> plants = map.getPlants();

        for (int row = 0; row < map.getBounds().getHeight(); row++){
            for (int column = 0; column < map.getBounds().getWidth(); column++){
                StackPane pane = new StackPane();

                if (plants.containsKey(new Vector2d(row, column))){
                    pane.setBackground(GRASS_CELL_COLOR);
                } else {
                    pane.setBackground(EMPTY_CELL_COLOR);
                }

                mapGrid.add(pane, row, column);
            }
        }
    }

    private void addAnimals(){
        Map<Vector2d, MapCell> mapCells = map.getMapCells();

        mapCells.values().forEach(mapCell -> {
            int row = mapCell.getCellPosition().getY();
            int column = mapCell.getCellPosition().getX();

            mapGrid.add(new Label("ANIMAL"), row, column);
        });

    }

    @Override
    public void mapChanged(WorldMap worldMap) {
        Platform.runLater(() -> {
            drawMap(worldMap);
        });
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}

