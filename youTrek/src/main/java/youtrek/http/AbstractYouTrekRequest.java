package youtrek.http;

import com.google.gson.GsonBuilder;

public class AbstractYouTrekRequest {
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }
}
