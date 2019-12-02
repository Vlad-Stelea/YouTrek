package youtrek.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import youtrek.models.ListOfPlaylists;
import youtrek.models.ListOfVideos;
import youtrek.models.Playlist;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class TestPlaylistDAO {
    @Before
    public void setupTests() throws Exception {
        DatabaseUtil.setSchema("testing");
    }

    @After
    public void deleteTestPlaylists() throws SQLException {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        dao.deletePlaylistByName("SomeTestPlaylist");
    }

    @Test
    public void testListPlaylists() throws SQLException {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        ListOfPlaylists playlists = dao.listPlaylists();
        assertNotNull(playlists);
    }

    @Test
    public void testPlaylistVideos() throws SQLException {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        ListOfVideos videos = null;
        videos = dao.getPlayListVideos(1);
        assertNotNull(videos);
    }

    @Test
    public void testCreatePlaylist() throws SQLException {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        ListOfPlaylists playlists = dao.listPlaylists();
        int numPlaylists = playlists.getNumPlaylists();

        Playlist test = null;
        test = dao.createPlaylist("SomeTestPlaylist");
        assertEquals("SomeTestPlaylist", test.name);

        ListOfPlaylists playlists2 = dao.listPlaylists();
        int numPlaylists2 = playlists2.getNumPlaylists();

        assertEquals((numPlaylists+1), (numPlaylists2));
    }

    @Test
    public void testGetPlaylist() throws Exception {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        Playlist pl = dao.getPlaylist(1);

        assertEquals("testPlaylist", pl.name);
        assertNotNull(pl);
    }

    @Test
    public void testAppendVideo() throws Exception {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        Playlist test = null;
        test = dao.createPlaylist("SomeTestPlaylist");
        ListOfVideos plVideos = dao.getPlayListVideos(test.id);
        int numVideos = plVideos.getNumVideos();

        dao.appendVideo(1, test.id);
        dao.appendVideo(2, test.id);

        ListOfVideos pl2Videos = dao.getPlayListVideos(test.id);
        int numVideos2 = pl2Videos.getNumVideos();

        assertEquals(numVideos + 2, numVideos2);
    }

    @Test
    public void testRemoveVideo() throws Exception {

    }

}
