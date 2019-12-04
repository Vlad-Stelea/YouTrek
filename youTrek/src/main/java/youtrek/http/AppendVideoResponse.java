package youtrek.http;

import youtrek.models.Playlist;

import java.util.Map;

public class AppendVideoResponse extends AbstractYouTrekResponse<Playlist> {

    public AppendVideoResponse(Playlist playlist, final Map<String, String> headers, final int statusCode) {
        super(playlist, headers, statusCode);
    }

}
