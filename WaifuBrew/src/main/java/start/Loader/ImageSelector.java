package start.Loader;

public enum ImageSelector {
    BUTTONS(0),
    VECTOR(1),
    BACKGROUND(2),
    CHARACTERS(3),
    EFFECTS(4),
    THUMBNAILS(5);

    private int value;

    private ImageSelector(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
