package youtrek.handlers;

import org.junit.Before;
import org.junit.Test;
import youtrek.db.DatabaseUtil;
import youtrek.http.RegisterTlpRequest;
import youtrek.http.RegisterTlpResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestRegisterTlpHandler {
    RegisterTlpHandler handler;
    RegisterTlpRequest request;
    String url = "www.testsite.org";

    @Before
    public void setup() {
        handler = new RegisterTlpHandler();
        request = new RegisterTlpRequest(url);
        DatabaseUtil.setSchema("testing");
    }
    @Test
    public void testSuccesfulResponse() {
        RegisterTlpResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }
}
