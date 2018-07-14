package start;

public enum ImageSelector {
    BUTTONS(0),
    VECTOR(1),
    BACKGROUND(2),
    CHARACTERS(3); // Not implemented yet

    private int value;

    private ImageSelector(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
