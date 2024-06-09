// AlertFactoryTest.java
package data_management.Alerts;

import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.factory.AlertFactory;
import com.alerts.factory.BloodOxygenAlertFactory;
import com.alerts.factory.BloodPressureAlertFactory;
import com.alerts.factory.ECGAlertFactory;

import static org.junit.jupiter.api.Assertions.*;

public class AlertFactoryTest {

    @Test
    public void testBloodPressureAlertFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("patient1", "high blood pressure", System.currentTimeMillis());
        assertNotNull(alert);
        assertTrue(alert instanceof Alert);
    }

    @Test
    public void testBloodOxygenAlertFactory() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("patient2", "low oxygen", System.currentTimeMillis());
        assertNotNull(alert);
        assertTrue(alert instanceof Alert);
    }

    @Test
    public void testECGAlertFactory() {
        AlertFactory factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("patient3", "irregular heart rate", System.currentTimeMillis());
        assertNotNull(alert);
        assertTrue(alert instanceof Alert);
    }
}
