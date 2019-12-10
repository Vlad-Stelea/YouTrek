package youtrek.handlers;

import org.junit.Before;
import org.junit.Test;
import youtrek.http.ListPlaylistsResponse;
import youtrek.http.ListPlaylistsRequest;
import youtrek.models.ListOfPlaylists;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestListPlaylistsHandler {
    ListPlaylistsHandler handler;
    ListOfPlaylists lop;
    ListPlaylistsRequest request;

    @Before
    public void setup() {
        handler = new ListPlaylistsHandler();
        lop = new ListOfPlaylists(new ArrayList<>());
        request = new ListPlaylistsRequest();
    }

    @Test
    public void testSuccessfulResponse() {
        ListPlaylistsResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }
}
