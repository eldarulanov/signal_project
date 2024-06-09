package data_management;

import com.alerts.Alert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlertTest {

    @Test
    public void testAlert() {
        Alert alert = new Alert("1", "Heart Rate above 100 bpm", 1234567890L);
        assertEquals("1", alert.getPatientId());
        assertEquals("Heart Rate above 100 bpm", alert.getCondition());
        assertEquals(1234567890L, alert.getTimestamp());
    }
}
