package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * A data generator to simulate patient's health data.
 * It is a interface with the different data types for health tracking
 */
public interface PatientDataGenerator {
    
    /**
     * Generates and outputs data for a specific patient.
     *
     * @param patientId The ID of the patient for whom data is being generated.
     * @param outputStrategy The output strategy to be used for dispatching generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
