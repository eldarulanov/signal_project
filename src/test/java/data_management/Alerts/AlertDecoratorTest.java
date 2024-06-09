package data_management.Alerts;

import com.alerts.Alert;
import com.alerts.decorator.PriorityAlertDecorator;
import com.alerts.decorator.RepeatedAlertDecorator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlertDecoratorTest {

    @Test
    public void testRepeatedAlertDecorator() {
        Alert alert = new Alert("patient1", "High measurement value", System.currentTimeMillis());
        Alert repeatedAlert = new RepeatedAlertDecorator(alert, 3, 1000);
        repeatedAlert.sendAlert(); // Verify if it prints 3 times with a 1-second interval
    }

    @Test
    public void testPriorityAlertDecorator() {
        Alert alert = new Alert("patient1", "High measurement value", System.currentTimeMillis());
        Alert priorityAlert = new PriorityAlertDecorator(alert, "High");
        priorityAlert.sendAlert(); // Verify if it prints priority and then the alert message
    }
}
