package youtrek.db;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import youtrek.models.Character;
import youtrek.models.ListOfVideos;
import youtrek.models.Video;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestVideoDAO {
    private final String dialogueFilter = "Crew";
    private final String characterFilter = "Spock";
    private final String titleFilter = "testVid";
    private final int id = 1;

    @Before
    public void setTestSchema() {
        DatabaseUtil.setSchema("testing");
    }

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
    public void testGetVideosFilteredByDialogue() throws Exception {
        testVideosFiltered(dialogueFilter);
    }

    @Test
    public void testGetVideosFilteredByTitle() throws Exception {
        testVideosFiltered(titleFilter);
    }

    @Test
    public void testVideosFilteredByCharacters() throws Exception {
        testVideosFiltered(characterFilter);
    }

    @Test
    public void testGetVideoById() throws Exception {
        VideoDAO dao = VideoDAO.getInstance();
        Video video = dao.getVideo(id);
        assertEquals(id, video.id);
    }

    @Test
    public void testCreateVideo() throws Exception {
        Video insertVideo = new Video("Test Name", "www.test.com", "TestString");
        int insertId = VideoDAO.getInstance().createVideo(insertVideo);
        Video acutallyInsertedVideo = VideoDAO.getInstance().getVideo(insertId);
        assertTrue(insertVideo.equals(acutallyInsertedVideo));
        //TODO delete the video coming in delete video PR
    }

    //Helper methods
    private void testVideosFiltered(String filter) throws Exception {
        VideoDAO dao = VideoDAO.getInstance();
        ListOfVideos videos = dao.getVideoSegments(filter);
        assertAllVideosFiltered(videos, filter);
    }

    private void assertAllVideosFiltered(ListOfVideos videos, String filter) {
        for(Video curVideo : videos) {
            assertTrue(curVideo.name.toLowerCase().contains(filter.toLowerCase())
                    || curVideo.dialogue.toLowerCase().contains(filter.toLowerCase())
                    || curVideo.characters.stream()
                        .map(String::toLowerCase) //Make sure all characters are in lowercase
                        .collect(Collectors.toList())
                        .contains(filter.toLowerCase())
            );
        }
    }
}
