package data_management;

import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.WebSocketClientReader;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WebSocketClientReaderTest {

    @Test
    public void testOnOpen() throws URISyntaxException {
        DataStorage mockDataStorage = mock(DataStorage.class);
        WebSocketClientReader client = new WebSocketClientReader(new URI("ws://localhost:8080"), mockDataStorage);
        ServerHandshake mockHandshake = mock(ServerHandshake.class);

        client.onOpen(mockHandshake);
        assertTrue(client.isConnected());
    }

    @Test
    public void testOnMessageValidFormat() {
        DataStorage mockDataStorage = mock(DataStorage.class);
        WebSocketClientReader client = new WebSocketClientReader(URI.create("ws://localhost:8080"), mockDataStorage);
        client.setConnected(true); // Simulate connection

        String message = "1,1627891234567,label,75.5";
        client.onMessage(message);

        verify(mockDataStorage, times(1)).addPatientData(1, 75.5, "label", 1627891234567L);
    }

    @Test
    public void testOnMessageInvalidFormat() {
        DataStorage mockDataStorage = mock(DataStorage.class);
        WebSocketClientReader client = new WebSocketClientReader(URI.create("ws://localhost:8080"), mockDataStorage);
        client.setConnected(true); // Simulate connection

        String invalidMessage = "1,1627891234567,label";
        client.onMessage(invalidMessage);

        verify(mockDataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    public void testOnClose() {
        DataStorage mockDataStorage = mock(DataStorage.class);
        WebSocketClientReader client = new WebSocketClientReader(URI.create("ws://localhost:8080"), mockDataStorage);

        client.onClose(1000, "Normal Closure", true);
        assertFalse(client.isConnected());
    }

    @Test
    public void testOnError() {
        DataStorage mockDataStorage = mock(DataStorage.class);
        WebSocketClientReader client = new WebSocketClientReader(URI.create("ws://localhost:8080"), mockDataStorage);
        Exception mockException = new Exception("Test Exception");

        client.onError(mockException);
        assertDoesNotThrow(() -> client.onError(mockException));
    }
}
