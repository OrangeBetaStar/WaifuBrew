package start;

public class TestClass {
    public static void main(String[] args) {
        ListenToBoolean interestingText = new ListenToBoolean(false);
        BooleanListener listener = new BooleanListener();
        interestingText.addPropertyChangeListener(listener);
        interestingText.setValue(true);
        interestingText.setValue(false);
        interestingText.setValue(false);
        interestingText.setValue(false);
        interestingText.setValue(false);
    }
}
