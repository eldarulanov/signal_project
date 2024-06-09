package com.data_management;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DataStorage {
    private ConcurrentMap<Integer, Patient> patientMap; // Stores patient objects indexed by their unique patient ID.

    public DataStorage() {
        this.patientMap = new ConcurrentHashMap<>();
    }

    public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        patientMap.compute(patientId, (id, patient) -> {
            if (patient == null) {
                patient = new Patient(patientId);
            }
            patient.addRecord(measurementValue, recordType, timestamp);
            return patient;
        });
    }

    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>();
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    // Adding the getPatient method
    public Patient getPatient(int patientId) {
        return patientMap.get(patientId);
    }
}
