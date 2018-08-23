package start;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ListenToBoolean {
    protected PropertyChangeSupport propertyChangeSupport;
    private boolean listenToMe;

    public ListenToBoolean (boolean defaultValue) {
        propertyChangeSupport = new PropertyChangeSupport(this);
        listenToMe = defaultValue;
    }

    public void setValue(boolean value) {

        System.out.println("old: " + listenToMe + " new: " + value);
        propertyChangeSupport.firePropertyChange("BooleanChange", listenToMe, value);
        this.listenToMe = value;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}

/*
public class MyTextTest {
    public static void main(String[] args) {
        MyClassWithText interestingText = new MyClassWithText();
        MyTextListener listener = new MyTextListener();
        interestingText.addPropertyChangeListener(listener);
        interestingText.setText("FRIST!");
        interestingText.setText("it's more like when you take a car, and you...");
    }
}
 */