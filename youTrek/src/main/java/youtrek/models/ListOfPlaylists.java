package youtrek.models;

import java.util.ArrayList;
import java.util.List;

public class ListOfPlaylists {
    List<Playlist> playlists;

    public ListOfPlaylists() {
        playlists = new ArrayList<>();
    }

    public ListOfPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void appendPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }
}
