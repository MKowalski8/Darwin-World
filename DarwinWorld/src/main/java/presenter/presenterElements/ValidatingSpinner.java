package presenter.presenterElements;

import javafx.beans.NamedArg;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class ValidatingSpinner extends Spinner<Integer> {

    private final int min;
    private final int max;
    private final int initialValue;

    public ValidatingSpinner(@NamedArg("min") int min, @NamedArg("max") int max, @NamedArg("initialValue") int initialValue) {
        super(min, max, initialValue);
        this.min = min;
        this.max = max;
        this.initialValue = initialValue;
        initializeSpinner();
    }

    private void initializeSpinner() {
        this.setEditable(true);
        setupValueFactory();
        setupEditorListeners();
    }

    private void setupValueFactory() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue);
        this.setValueFactory(valueFactory);

        this.getValueFactory().setConverter(new StringConverter<>() {
            @Override
            public Integer fromString(String string) {
                try {
                    int value = Integer.parseInt(string);

                    if (value < min || value > max) {
                        throw new ValueOutOfBoundsException(min, max);
                    }

                    return value;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid input");
                } catch (ValueOutOfBoundsException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }

            @Override
            public String toString(Integer object) {
                return object.toString();
            }
        });
    }

    private void setupEditorListeners() {
        TextField editor = this.getEditor();

        editor.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                handleEditorFocusLost(editor);
            }
        });
    }

    private void handleEditorFocusLost(TextField editor) {
        try {
            int newValue = this.getValueFactory().getConverter().fromString(editor.getText());
            this.getValueFactory().setValue(newValue);
        } catch (IllegalArgumentException e) {
            editor.setText(this.getValue().toString());
            showErrorTooltip(e.getMessage(), editor);
        }
    }

    private void showErrorTooltip(String message, TextField editor) {
        Tooltip errorTooltip = new Tooltip(message);
        errorTooltip.setAutoHide(true);
        errorTooltip.setShowDelay(Duration.seconds(0.1));
        errorTooltip.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        errorTooltip.show(editor.getScene().getWindow(),
                editor.localToScreen(editor.getBoundsInLocal()).getMinX(),
                editor.localToScreen(editor.getBoundsInLocal()).getMaxY());
    }

}
