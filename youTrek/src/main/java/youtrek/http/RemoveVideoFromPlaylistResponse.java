package youtrek.http;

import youtrek.models.Playlist;

import java.util.Map;

public class RemoveVideoFromPlaylistResponse extends AbstractYouTrekResponse<Playlist> {
    public RemoveVideoFromPlaylistResponse(Playlist playlist, final Map<String, String> headers, final int statusCode) {
        super(playlist, headers, statusCode);
    }
}
