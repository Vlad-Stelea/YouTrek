package youtrek.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import youtrek.models.ListOfPlaylists;
import youtrek.models.ListOfVideos;
import youtrek.models.Playlist;
import youtrek.models.Video;

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
        Playlist test = null;
        test = dao.createPlaylist("SomeTestPlaylist");
        dao.appendVideo(1, test.id);
        dao.appendVideo(2, test.id);
        ListOfVideos videos = null;
        videos = dao.getPlaylistVideos(test.id);
        assertEquals(2, videos.getNumVideos());
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

        Playlist pl = dao.createPlaylist("SomeTestPlaylist");
        Playlist pl2 = dao.getPlaylist(pl.id);

        assertEquals(pl, pl2);
        assertNotNull(pl2);
    }

    @Test
    public void testAppendVideo() throws Exception {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        Playlist test;
        test = dao.createPlaylist("SomeTestPlaylist");

        Playlist pl1video = dao.appendVideo(1, test.id);
        assertEquals(1, pl1video.videos.getNumVideos());

        Playlist pl2video = dao.appendVideo(2, test.id);
        assertEquals(2, pl2video.videos.getNumVideos());
    }

    @Test
    public void testDeletePlaylist() throws Exception {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        Playlist test = dao.createPlaylist("SomeTestPlaylist");
        ListOfPlaylists playlists = dao.listPlaylists();
        int sizePostInsert = playlists.getNumPlaylists();

        playlists = dao.deletePlaylist(test.id);
        int sizePostDelete = playlists.getNumPlaylists();

        assertEquals(sizePostDelete, sizePostInsert - 1);
    }

    @Test
    public void testRemoveVideo() throws Exception {
        // must test that not only videos are removed from playlist videos, but that insertions after deletions in proper order
        PlaylistDAO dao = PlaylistDAO.getInstance();
        Playlist test = dao.createPlaylist("SomeTestPlaylist");
        dao.appendVideo(1, test.id);
        dao.appendVideo(2, test.id);
        int numPostAppend = dao.getPlaylistVideos(test.id).getNumVideos();

        dao.removeVideoFromPlaylist(1, test.id);
        int numPostRemove = dao.getPlaylistVideos(test.id).getNumVideos();

        assertEquals(numPostAppend, numPostRemove+1);
        dao.appendVideo(1, test.id); // should now be AFTER 2 in order
        ListOfVideos lov = dao.getPlaylistVideos(test.id);
        boolean first = true;
        int firstid = -1, secondid = -1;
        for (Video v : lov) {
            if (first) {
                firstid = v.id;
                first = false;
            }
            else {
                secondid = v.id;
            }
        }
        assertTrue(firstid == 2 && secondid == 1);
    }
}
