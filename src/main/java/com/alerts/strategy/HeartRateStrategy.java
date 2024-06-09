package com.alerts.strategy;

import com.data_management.PatientRecord;

public class HeartRateStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(PatientRecord record) {
        double value = record.getMeasurementValue();
        return value < 60.0 || value > 100.0; // Example condition
    }
}
