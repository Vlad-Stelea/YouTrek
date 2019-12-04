package youtrek.http;

import youtrek.models.ListOfPlaylists;

import java.util.Map;

public class ListPlaylistsResponse extends AbstractYouTrekResponse<ListOfPlaylists> {

    public ListPlaylistsResponse(ListOfPlaylists playlists, final Map<String, String> headers, final int statusCode) {
        super(playlists, headers, statusCode);
    }

}
