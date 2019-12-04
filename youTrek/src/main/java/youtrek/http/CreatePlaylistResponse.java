package youtrek.http;

import youtrek.models.Playlist;

import java.util.Map;

public class CreatePlaylistResponse extends AbstractYouTrekResponse<Playlist> {

    public CreatePlaylistResponse(Playlist playlist, final Map<String, String> headers, final int statusCode) {
        super(playlist, headers, statusCode);
    }

}

