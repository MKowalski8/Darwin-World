package presenter.presenterElements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import worldElements.Animal;

import java.util.List;
import java.util.function.Consumer;

public class AnimalChooser {

    public static void showAnimalChooser(List<Animal> animals, Consumer<Animal> onAnimalChosen) {
        Stage stage = new Stage();

        ListView<Animal> listView = new ListView<>();
        ObservableList<Animal> items = FXCollections.observableArrayList(animals);
        listView.setItems(items);

        setAnimalsInCell(listView);
        listView.setStyle("-fx-font-size: 20px;");
        setClickBehavior(onAnimalChosen, listView, stage);

        VBox vbox = new VBox(listView);
        Scene scene = new Scene(vbox, 500, 200);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private static void setClickBehavior(Consumer<Animal> onAnimalChosen, ListView<Animal> listView, Stage stage) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Animal selectedAnimal = listView.getSelectionModel().getSelectedItem();
                if (selectedAnimal != null) {
                    onAnimalChosen.accept(selectedAnimal);
                    stage.close();
                }
            }
        });
    }

    private static void setAnimalsInCell(ListView<Animal> listView) {
        listView.setCellFactory(param -> new ListCell<Animal>() {
            @Override
            protected void updateItem(Animal animal, boolean empty) {
                super.updateItem(animal, empty);
                if (empty || animal == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String text = String.format("Energy: %s, ID: %s", animal.getEnergy(), animal.getId());
                    setText(text);
                    Circle circle = new Circle(10, getColor(animal));
                    setGraphic(circle);
                }
            }
        });
    }

    private static Color getColor(Animal animal) {
        int red = Math.min(255, Math.max(0, animal.getEnergy() * 10));
        return Color.rgb(red, 0, 0);
    }
}
