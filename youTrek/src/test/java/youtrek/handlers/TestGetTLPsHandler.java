package youtrek.handlers;

import org.junit.Before;
import org.junit.Test;
import youtrek.http.GetTLPsRequest;
import youtrek.http.GetTLPsResponse;
import youtrek.models.ListOfTlp;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestGetTLPsHandler {
    GetTLPsHandler handler;
    ListOfTlp lov;
    GetTLPsRequest request;

    @Before
    public void setup() {
        handler = new GetTLPsHandler();
        lov = new ListOfTlp(new ArrayList<>());
        request = new GetTLPsRequest();
    }

    @Test
    public void testSuccesfulResponse() {
        GetTLPsResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }
}
