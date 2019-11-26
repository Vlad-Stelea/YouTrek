package youtrek.handlers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import youtrek.http.CreatePlaylistRequest;
import youtrek.http.CreatePlaylistResponse;
import youtrek.models.Playlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


// TODO figure out how to do this in rollback mode (or just comment it out for the most part to avoid inserts)
public class TestCreatePlaylistHandler {
    CreatePlaylistHandler handler;
    Playlist pl;
    CreatePlaylistRequest request;

    @Before
    public void setup() {
        handler = new CreatePlaylistHandler();
        request = new CreatePlaylistRequest();
        request.setName("IntelliJ-testcase");
    }

    /**
    @Test
    public void testSuccesfulResponse() {
        CreatePlaylistResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }

    // Potential fix
    @After
    public void clean() {
        // sql remove from playlists where name="IntelliJ-testcase"
    }
    */
}
