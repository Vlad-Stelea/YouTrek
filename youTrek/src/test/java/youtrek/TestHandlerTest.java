package youtrek;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import youtrek.handlers.TestGatewayResponse;
import youtrek.handlers.TestHandler;

public class TestHandlerTest {
    @Test
    public void successfulResponse() {
        TestHandler app = new TestHandler();
        TestGatewayResponse result = (TestGatewayResponse) app.handleRequest(null, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
        assertTrue(content.contains("\"message\""));
        assertTrue(content.contains("\"hello world\""));
        assertTrue(content.contains("\"location\""));
    }
}
