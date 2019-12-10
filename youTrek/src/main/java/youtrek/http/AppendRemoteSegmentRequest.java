package youtrek.http;

import com.google.gson.GsonBuilder;
import youtrek.models.PublicSegment;

public class AppendRemoteSegmentRequest {
    int playlistId;
    PublicSegment ps;

    public AppendRemoteSegmentRequest() {

    }
    public AppendRemoteSegmentRequest(int playlistId, PublicSegment ps) {
        this.playlistId = playlistId;
        this.ps = ps;
    }

    public int getPlaylistId() {
        return this.playlistId;
    }

    public PublicSegment getPs() {
        return this.ps;
    }

    public void setPs(PublicSegment ps) {
        this.ps = ps;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }
}
