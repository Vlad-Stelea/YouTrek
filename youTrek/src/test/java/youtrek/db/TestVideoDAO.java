package youtrek.db;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import youtrek.models.ListOfVideos;
import youtrek.models.Video;

import java.util.stream.Collectors;

public class TestVideoDAO {

    private String dialogueFilter = "Crew";
    private String characterFilter = "Spock";
    private String titleFilter = "testVid";

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
        testVideosFitlered(dialogueFilter);
    }

    @Test
    public void testGetVideosFilteredByTitle() throws Exception {
        testVideosFitlered(titleFilter);
    }

    @Test
    public void testVideosFilteredByCharacters() throws Exception {
        testVideosFitlered(characterFilter);
    }

    //Helper methods
    private void testVideosFitlered(String filter) throws Exception {
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
