package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class AlertGenerator {
    private DataStorage dataStorage;

    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void evaluateData(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        for (PatientRecord record : records) {
            if (record.getMeasurementValue() > 100.0) { // Example condition
                triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "High measurement value", System.currentTimeMillis()));
            }
        }
    }

    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        System.out.println("Alert triggered: " + alert.getCondition() + " for patient ID " + alert.getPatientId());
    }
}
