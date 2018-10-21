package start.Containers;

public enum Mood {
    HAPPY(0),
    NORMAL(1),
    SAD(2),
    ANGRY(3);

    public final static int length = Mood.values().length;

    private int value;

    private Mood(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
