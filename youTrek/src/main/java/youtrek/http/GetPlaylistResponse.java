package youtrek.http;

import youtrek.models.Playlist;

import java.util.Map;

public class GetPlaylistResponse extends AbstractYouTrekResponse<Playlist> {

    public GetPlaylistResponse(Playlist playlist, final Map<String, String> headers, final int statusCode) {
        super(playlist, headers, statusCode);
    }

}
