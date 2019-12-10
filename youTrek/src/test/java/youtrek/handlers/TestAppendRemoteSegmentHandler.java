package youtrek.handlers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import youtrek.db.DatabaseUtil;
import youtrek.db.PlaylistDAO;
import youtrek.http.AppendRemoteSegmentRequest;
import youtrek.http.AppendRemoteSegmentResponse;
import youtrek.models.Playlist;
import youtrek.models.PublicSegment;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestAppendRemoteSegmentHandler {
    AppendRemoteSegmentHandler handler;
    Playlist pl;
    PublicSegment ps;
    AppendRemoteSegmentRequest request;

    @Before
    public void setup() throws SQLException {
        DatabaseUtil.setSchema("testing");
        PlaylistDAO dao = PlaylistDAO.getInstance();
        pl = dao.createPlaylist("SomeTestPlaylist");
        ps = new PublicSegment("test.com", "chekov", "hello");
        handler = new AppendRemoteSegmentHandler();
        request = new AppendRemoteSegmentRequest();
        request.setPs(ps);
        request.setPlaylistId(pl.id);
    }

    @After
    public void deleteTestPlaylists() throws SQLException {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        dao.deletePlaylistByName("SomeTestPlaylist");
    }

    @Test
    public void testSuccessfulResponse() {
        AppendRemoteSegmentResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }

}
