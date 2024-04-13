package com.cardio_generator.outputs;

/**
 * Interface to have a format/structure for outputing the data
 * Allows for various implementations due to it being an interface
 */
public interface OutputStrategy {

    /**
     * Outputs health data for a specific patient. Using certain data variables to track the patient. 
     * 
     *
     * @param patientId The ID of the patient
     * @param timestamp The timestamp of data recorded or generated 
     * @param label  label describing the type of data (e.g., "ECG", "Heart Rate").
     * @param data  actual data string to be output
     */
    void output(int patientId, long timestamp, String label, String data);
}
