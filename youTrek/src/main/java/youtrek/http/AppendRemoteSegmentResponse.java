package youtrek.http;

import youtrek.models.Playlist;

import java.util.Map;

public class AppendRemoteSegmentResponse extends AbstractYouTrekResponse<Playlist>{
    public AppendRemoteSegmentResponse(Playlist playlist, final Map<String, String> headers, final int statusCode) {
        super(playlist, headers, statusCode);
    }

}
