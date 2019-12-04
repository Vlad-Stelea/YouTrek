package youtrek.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class AbstractYouTrekResponse<T> {
    private final Map<String, String> headers;
    private final int statusCode;
    protected T body;

    protected AbstractYouTrekResponse(T body, Map<String, String> headers, int statusCode) {
        this.headers = headers;
        this.statusCode = statusCode;
        this.body = body;
    }

    public String getBody() {
        Gson gson = new GsonBuilder().
                        create();
        return gson.toJson(this.body);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
