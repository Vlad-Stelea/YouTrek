package youtrek.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import youtrek.models.Playlist;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CreatePlaylistResponse {

    final Playlist playlist;
    private final Map<String, String> headers;
    private final int statusCode;

    public CreatePlaylistResponse(Playlist playlist, final Map<String, String> headers, final int statusCode) {
        this.playlist = playlist;
        this.statusCode = statusCode;
        this.headers = Collections.unmodifiableMap(new HashMap<>(headers));
    }

    public String getBody() {
        Gson gson = new GsonBuilder().
                //registerTypeAdapter(ListOfVideos.class, new ListOfVideosAdapter()).
                        create();
        return gson.toJson(this.playlist);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

