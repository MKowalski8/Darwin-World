package presenter.smallerElements;

import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;


public class GenomeDrawing {
    public static HBox drawGenome(String text, int blueIndex) {
        HBox hbox = new HBox();
        int numberCtr = 0;

        for (int i = 0; i < text.length(); i++) {
            char number = text.charAt(i);
            Label label = new Label(String.valueOf(number));

            if (Character.isDigit(number)) {
                if (numberCtr == blueIndex) {
                    label.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
                }
                numberCtr++;
            }

            hbox.getChildren().add(label);
        }

        return hbox;
    }

}