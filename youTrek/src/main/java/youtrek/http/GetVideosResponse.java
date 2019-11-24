package youtrek.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import youtrek.models.ListOfVideos;

import youtrek.models.Video;
import youtrek.models.adapters.ListOfVideosAdapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetVideosResponse {
    final ListOfVideos videos;
    private final Map<String, String> headers;
    private final int statusCode;

    public GetVideosResponse(ListOfVideos videos, final Map<String, String> headers, final int statusCode) {
        this.videos = videos;
        this.statusCode = statusCode;
        this.headers = Collections.unmodifiableMap(new HashMap<>(headers));
    }

    public String getBody() {
        Gson gson = new GsonBuilder().
                //registerTypeAdapter(ListOfVideos.class, new ListOfVideosAdapter()).
                create();
        return gson.toJson(this.videos);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }


}
