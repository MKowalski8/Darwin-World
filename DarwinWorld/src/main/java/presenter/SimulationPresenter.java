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
        int cellWidth = 600/bounds.getWidth();
        int cellHeight = 600/bounds.getHeight();


        for (int i = 0; i <= bounds.getWidth()-1; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }

        for (int i = 0; i <= bounds.getHeight()-1; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
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

