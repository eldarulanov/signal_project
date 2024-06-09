// BloodPressureStrategy.java
package com.alerts.strategy;

import com.data_management.PatientRecord;

public class BloodPressureStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(PatientRecord record) {
        // Example logic for blood pressure alert
        return record.getMeasurementValue() > 120.0; // Example threshold
    }
}

