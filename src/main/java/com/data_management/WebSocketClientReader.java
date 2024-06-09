package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * WebSocketClientReader class extends WebSocketClient and implements DataReader.
 * This class connects to a WebSocket server, receives messages, stores data, and checks for
 * alerts into a DataStorage instance.
 */
public class WebSocketClientReader extends WebSocketClient implements DataReader {

    private DataStorage dataStorage;
    private boolean connected;

    /**
     * Constructs a new WebSocketClientReader with the specified server URI and data storage.
     *
     * @param serverUri    the URI of the WebSocket server
     * @param dataStorage  the data storage to store received data
     */
    public WebSocketClientReader(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
        this.connected = false;
    }

    /**
     * Called when the connection to the WebSocket server is opened.
     *
     * @param handshake  the handshake data from the server
     */
    @Override
    public void onOpen(ServerHandshake handshake) {
        connected = true;
        System.out.println("Connected to WebSocket server");
    }

    /**
     * Called when a message is received from the WebSocket server.
     *
     * @param message  the message received from the server
     */
    @Override
    public void onMessage(String message) {
        if (connected) {
            System.out.println("Received message: " + message);
            parseAndStore(message);
        }
    }

    /**
     * Called when the connection to the WebSocket server is closed.
     *
     * @param code     the status code indicating why the connection was closed
     * @param reason   the reason why the connection was closed
     * @param remote   whether the connection was closed remotely
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        connected = false;
        System.out.println("Connection closed with exit code " + code + " additional info: " + reason);
    }

    /**
     * Called when an error occurs.
     *
     * @param ex  the exception that occurred
     */
    @Override
    public void onError(Exception ex) {
        System.err.println("An error occurred: " + ex);
    }

    /**
     * Connects to the WebSocket server using the specified URI.
     *
     * @param uri  the URI of the WebSocket server
     * @throws URISyntaxException  if the URI is invalid
     */
    public void connectToWebSocket(String uri) throws URISyntaxException {
        URI serverUri = new URI(uri);
        this.uri = serverUri;
        this.connect();
    }

    /**
     * Parses and stores the received message into the data storage.
     *
     * @param message  the message received from the WebSocket server
     */
    private void parseAndStore(String message) {
        try {
            String[] parts = message.split(",");
            if (parts.length < 4) {
                throw new IllegalArgumentException("Invalid message format: " + message);
            }

            int patientId = Integer.parseInt(parts[0].trim());
            long timestamp = Long.parseLong(parts[1].trim());
            String label = parts[2].trim();
            String dataStr = parts[3].trim();

            // Remove percentage sign if present
            if (dataStr.endsWith("%")) {
                dataStr = dataStr.substring(0, dataStr.length() - 1);
            }

            double data = Double.parseDouble(dataStr);

            // Add the data to the storage
            dataStorage.addPatientData(patientId, data, label, timestamp);
        } catch (Exception e) {
            System.err.println("Error parsing message: " + message);
            e.printStackTrace();
        }
    }

    /**
     * Starts the WebSocket connection.
     */
    @Override
    public void start() {
        this.connect();
    }

    /**
     * Stops the WebSocket connection.
     */
    @Override
    public void stop() {
        this.close();
    }

    public boolean isConnected() {
        return connected;
    }

    public DataStorage getDataStorage() {
        return dataStorage;
    }

    /**
     * Helper method to simulate the connection establishment for testing purposes.
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * Main method to demonstrate the use of WebSocketClientReader.
     *
     * @param args  command line arguments
     */
    public static void main(String[] args) {
        try {
            DataStorage dataStorage = new DataStorage();
            URI uri = new URI("ws://localhost:8080");
            WebSocketClientReader client = new WebSocketClientReader(uri, dataStorage);
            client.start();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readData'");
    }
}
