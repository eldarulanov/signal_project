
// OxygenSaturationStrategy.java
package com.alerts.strategy;

import com.data_management.PatientRecord;

public class OxygenSaturationStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(PatientRecord record) {
        // Example logic for oxygen saturation alert
        return record.getMeasurementValue() < 90.0; // Example threshold
    }
}