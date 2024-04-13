package com.cardio_generator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.cardio_generator.generators.*;
import com.cardio_generator.outputs.*;

/**
 * The HealthDataSimulator simulates health data for patients.
 * Uses different data generators and outputs through configurable strategies.
 */
public class HealthDataSimulator {

    private static int patientCount = 50; // Default number of patients
    private static ScheduledExecutorService scheduler;
    private static OutputStrategy outputStrategy = new ConsoleOutputStrategy(); // Default output strategy
    private static final Random random = new Random();

    /**
     * Main method to start the simulation.
     *
     * @param args Configures the simulation settings.
     * @throws IOException If there are erros/exceptions for creating directors in the output. 
     */
    public static void main(String[] args) throws IOException {
        parseArguments(args);
        scheduler = Executors.newScheduledThreadPool(patientCount * 4);
        List<Integer> patientIds = initializePatientIds(patientCount);
        Collections.shuffle(patientIds); // Randomize the order of patient IDs
        scheduleTasksForPatients(patientIds);
    }

    /**
     * Parsess arguments to set up simulation parameters.
     *
     * @param args Array of command line arguments.
     * @throws IOException if directories needed for file output cannot be created.
     */
    private static void parseArguments(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                    printHelp();
                    System.exit(0);
                    break;
                case "--patient-count":
                    if (i + 1 < args.length) {
                        try {
                            patientCount = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err.println("Error: Invalid number of patients. Using default value: " + patientCount);
                        }
                    }
                    break;
                case "--output":
                    configureOutputStrategy(args[++i]);
                    break;
                default:
                    System.err.println("Unknown option '" + args[i] + "'");
                    printHelp();
                    System.exit(1);
            }
        }
    }

    /**
     * Configures output strategy based on  argument.
     *
     * @param outputArg the command line argument defining the output type and parameters.
     * @throws IOException If an output directory needs to be created and fails.
     */
    private static void configureOutputStrategy(String outputArg) throws IOException {
        if (outputArg.equals("console")) {
            outputStrategy = new ConsoleOutputStrategy();
        } else if (outputArg.startsWith("file:")) {
            String baseDirectory = outputArg.substring(5);
            Path outputPath = Paths.get(baseDirectory);
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
            }
            outputStrategy = new FileOutputStrategy(baseDirectory);
        } else if (outputArg.startsWith("websocket:")) {
            int port = Integer.parseInt(outputArg.substring(10));
            outputStrategy = new WebSocketOutputStrategy(port);
            System.out.println("WebSocket output will be on port: " + port);
        } else if (outputArg.startsWith("tcp:")) {
            int port = Integer.parseInt(outputArg.substring(4));
            outputStrategy = new TcpOutputStrategy(port);
            System.out.println("TCP socket output will be on port: " + port);
        } else {
            System.err.println("Unknown output type. Using default (console).");
        }
    }

    /**
     * Prints help info for using the simulator.
     */
    private static void printHelp() {
        System.out.println("Usage: java HealthDataSimulator [options]");
        System.out.println("Options:");
        System.out.println("  -h                       Show help and exit.");
        System.out.println("  --patient-count <count>  Specify the number of patients to simulate data for (default: 50).");
        System.out.println("  --output <type>          Define the output method. Options are:");
        System.out.println("                             'console' for console output,");
        System.out.println("                             'file:<directory>' for file output,");
        System.out.println("                             'websocket:<port>' for WebSocket output,");
        System.out.println("                             'tcp:<port>' for TCP socket output.");
        System.out.println("Example:");
        System.out.println("  java HealthDataSimulator --patient-count 100 --output websocket:8080");
    }

    /**
     * Initializes list of patient IDs.
     *
     * @param patientCount the number of patient IDs to generate.
     * @return a List of integers representing patient IDs.
     */
    private static List<Integer> initializePatientIds(int patientCount) {
        List<Integer> patientIds = new ArrayList<>();
        for (int i = 1; i <= patientCount; i++) {
            patientIds.add(i);
        }
        return patientIds;
    }

    /**
     * Schedules tasks for each patient based on generators and output strategy.
     *
     * @param patientIds a list of patient IDs for whom data generation tasks will be scheduled.
     */
    private static void scheduleTasksForPatients(List<Integer> patientIds) {
        ECGDataGenerator ecgDataGenerator = new ECGDataGenerator(patientCount);
        BloodSaturationDataGenerator bloodSaturationDataGenerator = new BloodSaturationDataGenerator(patientCount);
        BloodPressureDataGenerator bloodPressureDataGenerator = new BloodPressureDataGenerator(patientCount);
        BloodLevelsDataGenerator bloodLevelsDataGenerator = a BloodLevelsDataGenerator(patientCount);
        AlertGenerator alertGenerator = new AlertGenerator(patientCount);

        for (int patientId : patientIds) {
            scheduleTask(() -> ecgDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> bloodSaturationDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> bloodPressureDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.MINUTES);
            scheduleTask(() -> bloodLevelsDataGenerator.generate(patientId, outputStrategy), 2, TimeUnit.MINUTES);
            scheduleTask(() -> alertGenerator.generate(patientId, outputStrategy), 20, TimeUnit.SECONDS);
        }
    }

    /**
     * Schedules recurring task for data generation.
     *
     * @param task  task to be executed.
     * @param period  period between successive executions.
     * @param timeUnit  time unit of the period.
     */
    private static void scheduleTask(Runnable task, long period, TimeUnit timeUnit) {
        scheduler.scheduleAtFixedRate(task, random.nextInt(5), period, timeUnit);
    }
}
