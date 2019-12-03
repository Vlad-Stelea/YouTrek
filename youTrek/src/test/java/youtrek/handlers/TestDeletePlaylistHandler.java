package youtrek.handlers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import youtrek.db.DatabaseUtil;
import youtrek.db.PlaylistDAO;
import youtrek.http.DeletePlaylistRequest;
import youtrek.http.DeletePlaylistResponse;
import youtrek.models.Playlist;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestDeletePlaylistHandler {
    DeletePlaylistHandler handler;
    Playlist pl;
    DeletePlaylistRequest request;

    @Before
    public void setup() throws SQLException {
        DatabaseUtil.setSchema("testing");
        PlaylistDAO dao = PlaylistDAO.getInstance();
        Playlist toDelete = dao.createPlaylist("SomeTestPlaylist");

        handler = new DeletePlaylistHandler();
        request = new DeletePlaylistRequest();
        request.setId(toDelete.id);
    }

    @After
    public void deleteTestPlaylists() throws SQLException {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        dao.deletePlaylistByName("SomeTestPlaylist");
    }

    @Test
    public void testSuccesfulResponse() {
        DeletePlaylistResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }
}
