package data_management;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.List;

public class AlertGeneratorTest {

    private AlertGenerator alertGenerator;
    private DataStorage dataStorage;

    @BeforeEach
    public void setUp() {
        dataStorage = mock(DataStorage.class);
        alertGenerator = new AlertGenerator(dataStorage);
    }

    @Test
    public void testEvaluateDataTriggersAlert() {
        Patient patient = mock(Patient.class);
        when(patient.getPatientId()).thenReturn(1);

        PatientRecord highValueRecord = new PatientRecord(1, 150.0, "Test", System.currentTimeMillis());
        List<PatientRecord> records = List.of(highValueRecord);

        when(dataStorage.getPatient(anyInt())).thenReturn(patient);
        when(patient.getRecords(anyLong(), anyLong())).thenReturn(records);

        alertGenerator.evaluateData(patient);

        verify(patient, times(1)).getRecords(anyLong(), anyLong());
    }
}
