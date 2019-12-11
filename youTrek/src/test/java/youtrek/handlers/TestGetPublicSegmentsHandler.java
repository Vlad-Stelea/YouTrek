package youtrek.handlers;

import org.junit.Before;
import org.junit.Test;
import youtrek.http.GetPublicSegmentsRequest;
import youtrek.http.GetPublicSegmentsResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestGetPublicSegmentsHandler {
    GetPublicSegmentsHandler handler;
    GetPublicSegmentsRequest request;

    @Before
    public void setup() {
        handler = new GetPublicSegmentsHandler();
        request = new GetPublicSegmentsRequest();
    }

    @Test
    public void testSuccessfulResponse() {
        GetPublicSegmentsResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        System.out.println(content);
        assertNotNull(content);
    }
}
