// AlertStrategy.java
package com.alerts.strategy;

import com.data_management.PatientRecord;

public interface AlertStrategy {
    boolean checkAlert(PatientRecord record);
}
