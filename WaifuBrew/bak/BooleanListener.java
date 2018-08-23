package start;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BooleanListener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("BooleanChange")) {
            System.out.println("There was a change with boolean!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(event.getNewValue().toString());
        }
    }
}