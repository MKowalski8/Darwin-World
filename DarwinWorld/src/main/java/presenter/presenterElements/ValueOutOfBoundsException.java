package presenter.presenterElements;

public class ValueOutOfBoundsException extends Exception {

    public ValueOutOfBoundsException(int min, int max) {
        super(String.format("Value has to be between %d and %d", min, max));
    }

}