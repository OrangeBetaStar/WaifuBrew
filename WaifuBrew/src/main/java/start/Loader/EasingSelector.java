package start.Loader;

public enum EasingSelector {
    LINEAR(0),
    IN_SINE(1),
    OUT_SINE(2),
    IN_OUT_EXPO(3);

    public final static int length = EasingSelector.values().length;

    private int value;

    private EasingSelector(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}