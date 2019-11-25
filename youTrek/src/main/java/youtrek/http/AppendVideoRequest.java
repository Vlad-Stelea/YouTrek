package youtrek.http;

import com.google.gson.GsonBuilder;

public class AppendVideoRequest {
    int playlist_id;
    int video_id;

    public AppendVideoRequest() {

    }
    public AppendVideoRequest(int playlist_id, int video_id) {
        this.playlist_id = playlist_id;
        this.video_id = video_id;
    }

    public int getVideoId() {
        return this.video_id;
    }
    public int getPlaylistId() {
        return this.playlist_id;
    }

    public void setVideoId(int id) {
        this.video_id = id;
    }
    public void setPlaylistId(int id) {
        this.playlist_id = id;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }

}