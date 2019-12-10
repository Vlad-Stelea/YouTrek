package youtrek.handlers;

import org.junit.Before;
import org.junit.Test;
import youtrek.http.GetPlaylistRequest;
import youtrek.http.GetPlaylistResponse;
import youtrek.models.Playlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestGetPlaylistHandler {
    GetPlaylistHandler handler;
    Playlist pl;
    GetPlaylistRequest request;

    @Before
    public void setup() {
        handler = new GetPlaylistHandler();
        request = new GetPlaylistRequest();
        request.setId(3);
    }

    @Test
    public void testSuccessfulResponse() {
        GetPlaylistResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }
}
