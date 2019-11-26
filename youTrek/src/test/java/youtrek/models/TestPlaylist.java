package youtrek.models;

import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertTrue;


public class TestPlaylist {
    @Test
    public void testPlaylistEquals() {
        Playlist p1 = new Playlist(1, "Someplaylist");
        Playlist p2 = new Playlist(2, "Someplaylist"); // not equals even though same name
        Playlist p3 = new Playlist(1, "Renamed playlist");
        assertTrue(!p1.equals(p2));
        assertTrue(p1.equals(p3));
    }

    @Test
    public void testAppendVideo() {
        Playlist p1 = new Playlist(1, "Test");
        Video v1 = new Video(1, "testvideo", "www.xscratch.com", "chekov", null);
        assertTrue(p1.videos.videos.size() == 0);
        p1.appendVideo(v1);
        p1.appendVideo(v1);
        assertTrue(p1.videos.videos.size() == 2);
    }
}
