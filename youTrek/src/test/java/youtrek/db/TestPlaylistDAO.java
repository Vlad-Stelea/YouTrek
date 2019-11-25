package youtrek.db;

import org.junit.Test;
import youtrek.models.ListOfPlaylists;
import youtrek.models.Playlist;

import static org.junit.Assert.assertNotNull;

public class TestPlaylistDAO {
    @Test
    public void testListPlaylists() throws Exception {
        PlaylistDAO dao = PlaylistDAO.getInstance();
        ListOfPlaylists playlists = dao.listPlaylists();
        assertNotNull(playlists);
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
