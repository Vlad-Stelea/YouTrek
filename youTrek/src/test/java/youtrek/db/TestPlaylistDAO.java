package youtrek.db;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import youtrek.models.ListOfPlaylists;
import youtrek.models.Playlist;

public class TestPlaylistDAO {

    @Before
    public void setTestSchema() {
        DatabaseUtil.setSchema("testing");
    }

    @Test
    public void testListPlaylists() throws Exception {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        ListOfPlaylists playlists = dao.listPlaylists();
        assertNotNull(playlists);
    }

    @Test
    public void testPlaylistVideos() {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        Playlist pl = null;
    }

    @Test
    public void testCreatePlaylist() {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        Playlist pl = null;
    }

    @Test
    public void testGetPlaylist() throws Exception {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        Playlist pl = dao.getPlaylist(1);
        System.out.println(pl.id + "\t" + pl.name);
        assertNotNull(pl);
    }

    //TODO figure out how to test delete/add functions
}
