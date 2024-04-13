package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements  OutputStrategy interface to outpiut data into files.
 * Class manages file operations like creating directories, resolving file paths, and data writing
 */
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory;  // Base directory path where files will be created

    // Naming convention for constants in Java uses UPPER_CASE
    public final ConcurrentHashMap<String, String> FILE_MAP = new ConcurrentHashMap<>();

    /**
     * Created FileOutputStrategy with a base directory (which the user creates) for file storage.
     *
     * @param baseDirectory The directory where data files will be stored.
     */
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs data to file . If the file does not exist, it is created.
     * If it does exist, data is added to the end.
     *
     * @param patientId  ID of patient.
     * @param timestamp  Timestamp of data entry.
     * @param label The label indicating the type of data (e.g., "ECG").
     * @param data The actual data 
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Ensure the directory exists; create if it doesn't
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        
        // Compute file path from label, creating new file if necessary
        String filePath = FILE_MAP.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Attempt to write data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (IOException e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
