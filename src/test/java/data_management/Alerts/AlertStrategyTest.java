package data_management.Alerts;

import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.alerts.strategy.AlertStrategy;
import com.alerts.strategy.BloodPressureStrategy;
import com.alerts.strategy.HeartRateStrategy;
import com.alerts.strategy.OxygenSaturationStrategy;

public class AlertStrategyTest {

    @Test
    public void testBloodPressureStrategy() {
        AlertStrategy strategy = new BloodPressureStrategy();
        PatientRecord record = new PatientRecord(1, 130.0, "BloodPressure", System.currentTimeMillis()); // Ensure correct constructor
        assertTrue(strategy.checkAlert(record));

        record = new PatientRecord(1, 110.0, "BloodPressure", System.currentTimeMillis()); // Ensure correct constructor
        assertFalse(strategy.checkAlert(record));
    }

    @Test
    public void testHeartRateStrategy() {
        AlertStrategy strategy = new HeartRateStrategy();
        PatientRecord record = new PatientRecord(1, 55.0, "HeartRate", System.currentTimeMillis()); // Ensure correct constructor
        assertTrue(strategy.checkAlert(record));

        record = new PatientRecord(1, 70.0, "HeartRate", System.currentTimeMillis()); // Ensure correct constructor
        assertFalse(strategy.checkAlert(record));
    }

    @Test
    public void testOxygenSaturationStrategy() {
        AlertStrategy strategy = new OxygenSaturationStrategy();
        PatientRecord record = new PatientRecord(1, 85.0, "OxygenSaturation", System.currentTimeMillis()); // Ensure correct constructor
        assertTrue(strategy.checkAlert(record));

        record = new PatientRecord(1, 95.0, "OxygenSaturation", System.currentTimeMillis()); // Ensure correct constructor
        assertFalse(strategy.checkAlert(record));
    }
}
