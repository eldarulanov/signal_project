package data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PatientTest {

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient(1);
    }

    @Test
    void testAddRecordAndGetRecords() {
        patient.addRecord(100.0, "HeartRate", 1714376789050L);
        patient.addRecord(200.0, "HeartRate", 1714376789051L);
        patient.addRecord(150.0, "BloodPressure", 1714376789052L);

        List<PatientRecord> records = patient.getRecords(1714376789050L, 1714376789051L);
        assertEquals(2, records.size(), "Should retrieve two records");
        assertEquals(100.0, records.get(0).getMeasurementValue(), 0.01, "First record value should be 100.0");
        assertEquals(200.0, records.get(1).getMeasurementValue(), 0.01, "Second record value should be 200.0");

        records = patient.getRecords(1714376789050L, 1714376789052L);
        assertEquals(3, records.size(), "Should retrieve three records");
    }
}
