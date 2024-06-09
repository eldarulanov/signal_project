package data_management;

import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.WebSocketClientReader;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WebSocketClientErrorHandlingTest {

    @Test
    public void testHandleConnectionLoss() throws URISyntaxException {
        DataStorage mockDataStorage = mock(DataStorage.class);
        WebSocketClientReader client = new WebSocketClientReader(new URI("ws://localhost:8080"), mockDataStorage);
        ServerHandshake mockHandshake = mock(ServerHandshake.class);

        client.onOpen(mockHandshake);
        assertTrue(client.isConnected());

        client.onClose(1006, "Abnormal Closure", true); // 1006 indicates abnormal closure
        assertFalse(client.isConnected());

        // Ensure no data processing happens after connection loss
        String message = "1,1627891234567,label,75.5";
        client.onMessage(message);
        verify(mockDataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    public void testHandleInvalidDataFormat() {
        DataStorage mockDataStorage = mock(DataStorage.class);
        WebSocketClientReader client = new WebSocketClientReader(URI.create("ws://localhost:8080"), mockDataStorage);

        String invalidMessage = "invalid,data,format";
        client.onMessage(invalidMessage);

        // Verify that no data is added to the storage for invalid format
        verify(mockDataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    public void testHandleNetworkError() {
        DataStorage mockDataStorage = mock(DataStorage.class);
        WebSocketClientReader client = new WebSocketClientReader(URI.create("ws://localhost:8080"), mockDataStorage);
        Exception mockException = new Exception("Network Error");

        client.onError(mockException);
        // Since onError just prints the stack trace, ensure no additional exceptions are thrown
        assertDoesNotThrow(() -> client.onError(mockException));
    }
}
