package youtrek.handlers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import youtrek.db.DatabaseUtil;
import youtrek.db.PlaylistDAO;
import youtrek.http.CreatePlaylistRequest;
import youtrek.http.CreatePlaylistResponse;
import youtrek.models.Playlist;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TestCreatePlaylistHandler {
    CreatePlaylistHandler handler;
    Playlist pl;
    CreatePlaylistRequest request;

    @Before
    public void setup() {
        DatabaseUtil.setSchema("testing");
        handler = new CreatePlaylistHandler();
        request = new CreatePlaylistRequest();
        request.setName("SomeTestPlaylist");
    }

    @After
    public void deleteTestPlaylists() throws SQLException {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        dao.deletePlaylistByName("SomeTestPlaylist");
    }

    @Test
    public void testSuccesfulResponse() {
        CreatePlaylistResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }

}
