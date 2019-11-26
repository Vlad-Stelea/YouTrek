package youtrek.http;

import com.google.gson.GsonBuilder;

public class CreatePlaylistRequest {
    String name;

    public CreatePlaylistRequest() {

    }
    public CreatePlaylistRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }

}
