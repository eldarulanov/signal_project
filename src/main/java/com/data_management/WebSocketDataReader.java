
/* 
package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

public class WebSocketDataReader implements DataReader {

    private DataStorage dataStorage;
    private WebSocketClient webSocketClient;
    private String serverUri;

    public WebSocketDataReader(String serverUri, DataStorage dataStorage) {
        this.serverUri = serverUri;
        this.dataStorage = dataStorage;
    }

    private void setupWebSocketClient(String uri) throws URISyntaxException, IOException, InterruptedException {
        webSocketClient = new WebSocketClient(new URI(uri)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Connected to WebSocket server");
            }

            @Override
            public void onMessage(String message) {
                try {
                    handleData(dataStorage, message);
                } catch (IOException e) {
                    onError(e);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Disconnected from WebSocket server: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("Error occurred: " + ex.getMessage());
                ex.printStackTrace();
            }
        };
        if (!webSocketClient.connectBlocking()) {
            throw new IOException("Failed to connect to WebSocket server at " + uri);
        }
    }

    @Override
    public void connect(String uri) throws IOException {
        try {
            try {
                setupWebSocketClient(uri);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            throw new IOException("URI Syntax Error: " + e.getMessage(), e);
        }
    }

    @Override
    public void disconnect() {
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }

    @Override
    public void handleData(DataStorage dataStorage, String message) throws IOException {
        String[] parts = message.split(",");
        if (parts.length != 4) {
            throw new IOException("Invalid message format: " + message);
        }
        try {
            int patientId = Integer.parseInt(parts[0]);
            long timestamp = Long.parseLong(parts[1]);
            String label = parts[2];
            double data = Double.parseDouble(parts[3]);
            dataStorage.addPatientData(patientId, data, label, timestamp);
        } catch (NumberFormatException e) {
            throw new IOException("Error processing data message", e);
        }
    }
}
*/