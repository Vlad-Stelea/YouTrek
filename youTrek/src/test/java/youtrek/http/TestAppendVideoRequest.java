package youtrek.http;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestAppendVideoRequest {
    @Test
    public void TestComplexConstructor() {
        int playlist = 1;
        int video = 3;
        AppendVideoRequest request = new AppendVideoRequest(playlist, video);
        assertEquals(playlist, request.getPlaylistId());
        assertEquals(video, request.getVideoId());
    }
}
