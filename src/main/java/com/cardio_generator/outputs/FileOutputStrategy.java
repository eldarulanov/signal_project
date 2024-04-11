package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

public class FileOutputStrategy implements OutputStrategy {

    // this variable name should've been in camelCase which i Changed
    private String baseDirectory;

    // since it's a final variable, file_map should've been to changed to all uppercase
    public final ConcurrentHashMap<String, String> FILE_MAP = new ConcurrentHashMap<>();

    public FileOutputStrategy(String baseDirectory) {
        // kept it constant with the lowercase
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // filepath should've been changed to proper camelcase to filePath not FilePath
        String filePath = FILE_MAP.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        //writing the data to the file now
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (IOException e) {
            // Catch specific exception
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
