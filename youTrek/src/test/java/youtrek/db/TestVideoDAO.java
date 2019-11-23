package youtrek.db;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import youtrek.models.Video;
import java.util.ArrayList;


public class TestVideoDAO {
    @Test
    public void testGetVideo() throws Exception {
        VideoDAO dao = VideoDAO.getInstance();
        Video testVid = null;
        testVid = dao.getVideo(1); // id 1 corresponds to a video name "testVid" with url "C2-Mental_Disease" in mySQL
        String videoName = "testVid";
        String videoUrl = "/C2-Mental_Disease.ogg";
        assertTrue(testVid.name.equals(videoName) && testVid.url.equals(videoUrl));
    }

    @Test
    public void testGetVideos() throws Exception {
        VideoDAO dao = VideoDAO.getInstance();
        ArrayList<Video> videos = dao.getVideoSegments();
        assertTrue(videos.size() > 0);
    }
}
