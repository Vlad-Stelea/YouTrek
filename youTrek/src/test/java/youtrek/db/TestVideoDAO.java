package youtrek.db;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import youtrek.models.ListOfVideos;
import youtrek.models.Video;

public class TestVideoDAO {

    private String filter = "Crew";

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
        ListOfVideos videos = dao.getVideoSegments();
        assertNotNull(videos);
    }

    @Test
    public void testGetVideosFiltered() throws Exception {
        VideoDAO dao = VideoDAO.getInstance();
        ListOfVideos videos = dao.getVideoSegments(filter);
        assertAllVideosFiltered(videos, filter);
    }

    private void assertAllVideosFiltered(ListOfVideos videos, String filter) {
        for(Video curVideo : videos) {
            //TODO Add support for checking
            assertTrue(curVideo.name.toLowerCase().contains(filter.toLowerCase())
                    || curVideo.dialogue.toLowerCase().contains(filter.toLowerCase()));
        }
    }
}
