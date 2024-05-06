package data_management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.data_management.DataReader;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class DataStorageTest {

    @Mock
    private DataReader reader; // Mock the DataReader

    private DataStorage storage;

    @BeforeEach
    void setUp() {
        storage = new DataStorage(); // Assuming DataStorage does not require DataReader
        // Configure your mocked reader here
        // Example: when(reader.readData()).thenReturn(Arrays.asList(
        //    new PatientRecord(1, 100.0, "WhiteBloodCells", 1714376789050L),
        //    new PatientRecord(1, 200.0, "WhiteBloodCells", 1714376789051L)));
    }

    @Test
    void testAddAndGetRecords() {
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size(), "Should retrieve two records");
        assertEquals(100.0, records.get(0).getMeasurementValue(), 0.01, "First record value should be 100.0");
    }
}
