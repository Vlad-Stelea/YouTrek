package youtrek.handlers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import youtrek.http.AppendVideoRequest;
import youtrek.http.AppendVideoResponse;
import youtrek.models.Playlist;
import youtrek.models.Video;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestAppendVideoHandler {
    AppendVideoHandler handler;
    Playlist pl;
    Video vid;
    AppendVideoRequest request;

    @Before
    public void setup() {
        handler = new AppendVideoHandler();
        request = new AppendVideoRequest();
        request.setVideoId(1);
        request.setPlaylistId(5);
    }

    /**
     @Test
     public void testSuccesfulResponse() {
     AppendVideoResponse result = handler.handleRequest(request, null);
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
