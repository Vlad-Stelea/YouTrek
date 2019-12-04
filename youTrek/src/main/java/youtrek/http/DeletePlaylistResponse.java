package youtrek.http;

import youtrek.models.ListOfPlaylists;

import java.util.Map;

public class DeletePlaylistResponse extends AbstractYouTrekResponse<ListOfPlaylists> {
    public DeletePlaylistResponse(ListOfPlaylists playlists, final Map<String, String> headers, final int statusCode) {
        super(playlists, headers, statusCode);
    }
}
