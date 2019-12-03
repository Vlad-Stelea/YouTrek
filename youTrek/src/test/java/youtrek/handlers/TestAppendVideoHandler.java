package youtrek.handlers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import youtrek.db.DatabaseUtil;
import youtrek.db.PlaylistDAO;
import youtrek.http.AppendVideoRequest;
import youtrek.http.AppendVideoResponse;
import youtrek.models.Playlist;
import youtrek.models.Video;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestAppendVideoHandler {
    AppendVideoHandler handler;
    Playlist pl;
    Video vid;
    AppendVideoRequest request;

    @Before
    public void setup() throws SQLException {
        DatabaseUtil.setSchema("testing");
        PlaylistDAO dao = PlaylistDAO.getInstance();
        pl = dao.createPlaylist("SomeTestPlaylist");
        handler = new AppendVideoHandler();
        request = new AppendVideoRequest();
        request.setVideoId(1);
        request.setPlaylistId(pl.id);
    }

    @After
    public void deleteTestPlaylists() throws SQLException {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        dao.deletePlaylistByName("SomeTestPlaylist");
    }

     @Test
     public void testSuccesfulResponse() {
     AppendVideoResponse result = handler.handleRequest(request, null);
     assertEquals(result.getStatusCode(), 200);
     assertEquals(result.getHeaders().get("Content-Type"), "application/json");
     String content = result.getBody();
     assertNotNull(content);
     }

}
