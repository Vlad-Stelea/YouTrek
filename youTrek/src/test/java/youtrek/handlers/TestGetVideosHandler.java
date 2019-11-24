package youtrek.handlers;

import org.junit.Before;
import org.junit.Test;
import youtrek.http.GetVideosRequest;
import youtrek.http.GetVideosResponse;
import youtrek.models.ListOfVideos;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestGetVideosHandler {
    GetVideosHandler handler;
    ListOfVideos lov;
    GetVideosRequest request;

    @Before
    public void setup() {
        handler = new GetVideosHandler();
        lov = new ListOfVideos(new ArrayList<>());
        request = new GetVideosRequest();
    }

    @Test
    public void testSuccesfulResponse() {
        GetVideosResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }
}
