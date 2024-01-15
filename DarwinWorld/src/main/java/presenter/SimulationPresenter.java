package presenter;

import components.Boundary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import maps.WorldMap;
import simulations.MapChangeListener;


public class SimulationPresenter implements MapChangeListener {

    private static final double CELL_WIDTH = 35;
    private static final double CELL_HEIGHT = 35;

    private WorldMap map;

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
        infoLabel.setText(mapType);
        drawMap(map);
    }

    public void drawMap(WorldMap worldMap) {
        clearGrid();

        Boundary bounds = worldMap.getBounds();

        for (int i = 0; i <= bounds.width() + 1; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for (int i = 0; i <= bounds.height() + 1; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        drawMapElements();
    }

    private void drawMapElements() {

    }


    @Override
    public void mapChanged(WorldMap worldMap, String message) {
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

