package youtrek.http;

import com.google.gson.GsonBuilder;

public class RemoveVideoFromPlaylistRequest {
    int playlistId;
    int videoId;

    public RemoveVideoFromPlaylistRequest() {

    }
    public RemoveVideoFromPlaylistRequest(int playlistId, int videoId) {
        this.playlistId = playlistId;
        this.videoId = videoId;
    }

    public int getVideoId() {
        return this.videoId;
    }
    public int getPlaylistId() {
        return this.playlistId;
    }

    public void setVideoId(int id) {
        this.videoId = id;
    }
    public void setPlaylistId(int id) {
        this.playlistId = id;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }

}
