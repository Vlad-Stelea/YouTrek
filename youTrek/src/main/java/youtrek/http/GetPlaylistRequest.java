package youtrek.http;

import com.google.gson.GsonBuilder;

public class GetPlaylistRequest {
    int id;

    public GetPlaylistRequest() {

    }
    public GetPlaylistRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }

}
