package com.alerts;

import com.alerts.decorator.PriorityAlertDecorator;
import com.alerts.decorator.RepeatedAlertDecorator;
import com.alerts.factory.AlertFactory;
import com.alerts.factory.BloodPressureAlertFactory;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class AlertGenerator {
    private DataStorage dataStorage;

    public AlertGenerator() {
        this.dataStorage = DataStorage.getInstance();
    }

    public void evaluateData(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        for (PatientRecord record : records) {
            AlertFactory alertFactory;
            if (record.getMeasurementValue() > 100.0) { // Example condition
                alertFactory = new BloodPressureAlertFactory();
                Alert alert = alertFactory.createAlert(String.valueOf(patient.getPatientId()), "High measurement value", System.currentTimeMillis());
                
                // Apply decorators
                Alert decoratedAlert = new PriorityAlertDecorator(alert, "High");
                decoratedAlert = new RepeatedAlertDecorator(decoratedAlert, 3, 1000); // Repeat 3 times with 1 second interval
                
                triggerAlert(decoratedAlert);
            }
            // Add other conditions and factories as needed
        }
    }

    private void triggerAlert(Alert alert) {
        alert.sendAlert();
    }
}
