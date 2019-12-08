package youtrek.handlers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import youtrek.db.DatabaseUtil;
import youtrek.db.PlaylistDAO;
import youtrek.http.SetVideoAvailabilityRequest;
import youtrek.http.SetVideoAvailabilityResponse;
import youtrek.models.Playlist;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestSetVideoAvailabilityHander {
    SetVideoAvailabilityHandler handler;
    Playlist pl;
    SetVideoAvailabilityRequest request;

    @Before
    public void setup() throws SQLException {
        DatabaseUtil.setSchema("testing");
        PlaylistDAO dao = PlaylistDAO.getInstance();
        pl = dao.createPlaylist("SomeTestPlaylist");
        pl = dao.appendVideo(1, pl.id);
        handler = new SetVideoAvailabilityHandler();
        request = new SetVideoAvailabilityRequest();
        request.setVideoId(1);
        request.setIsAvail(true);
    }

    @After
    public void deleteTestPlaylists() throws SQLException {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        dao.deletePlaylistByName("SomeTestPlaylist");
    }

    @Test
    public void testSuccesfulResponse() {
        SetVideoAvailabilityResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }
}
