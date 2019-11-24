package youtrek.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import youtrek.models.ListOfPlaylists;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ListPlaylistsResponse {
    final ListOfPlaylists playlists;
    private final Map<String, String> headers;
    private final int statusCode;

    public ListPlaylistsResponse(ListOfPlaylists playlists, final Map<String, String> headers, final int statusCode) {
        this.playlists = playlists;
        this.statusCode = statusCode;
        this.headers = Collections.unmodifiableMap(new HashMap<>(headers));
    }

    public String getBody() {
        Gson gson = new GsonBuilder().
                //registerTypeAdapter(ListOfVideos.class, new ListOfVideosAdapter()).
                        create();
        return gson.toJson(this.playlists);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }


}
