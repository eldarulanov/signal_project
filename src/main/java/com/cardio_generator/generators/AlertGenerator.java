package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

/**
 * Creates alert data for patients and outputs using output strategy. 
 * Class manages the alerts for each patient.
 */
public class AlertGenerator implements PatientDataGenerator {

    private static final Random randomGenerator = new Random();
    private boolean[] alertStates; // false = resolved, true = triggered

    /**
     * Constructs an AlertGenerator for the specified number of patients.
     *
     * @param patientCount The number of patients to manage alerts for.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1]; // +1 because patient IDs are presumably 1-based
    }

    /**
     * Creates and outputs alert data for patient.
     * Method simulates probability of alerts being triggered or resolved based on random fluctuations.
     *
     * @param patientId The ID of the patient for whom to generate alert data.
     * @param outputStrategy The output strategy to be used for outputting the alert data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                // 90% chance to resolve the alert
                if (randomGenerator.nextDouble() < 0.9) {
                    alertStates[patientId] = false;
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double lambda = 0.1; // Average rate (alerts per period)
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}