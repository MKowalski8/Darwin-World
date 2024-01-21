package presenter.smallerElements;

import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class GenomeDrawing {
    public static ScrollPane drawGenome(String text, int blueIndex) {
        HBox hbox = new HBox(5);

        for (int i = 0; i < text.length(); i++) {
            Label label = new Label(String.valueOf(text.charAt(i)));
            
            // Set the background color to blue
            label.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-padding: 5;");

            hbox.getChildren().add(label);
        }

        return getScrollPane(hbox);
    }

    private static ScrollPane getScrollPane(HBox hbox) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(hbox);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return scrollPane;
    }
}
