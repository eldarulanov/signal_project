
// HeartRateStrategy.java
package com.alerts.strategy;

import com.data_management.PatientRecord;

public class HeartRateStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(PatientRecord record) {
        // Example logic for heart rate alert
        return record.getMeasurementValue() < 60.0 || record.getMeasurementValue() > 100.0; // Example thresholds
    }
}