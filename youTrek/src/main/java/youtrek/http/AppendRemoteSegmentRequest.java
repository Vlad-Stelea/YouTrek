package youtrek.http;

import com.google.gson.GsonBuilder;
import youtrek.models.PublicSegment;

public class AppendRemoteSegmentRequest {
    int playlistId;
    String url;
    String characters;
    String text;
    // PublicSegment ps;

    public AppendRemoteSegmentRequest() {

    }
    public AppendRemoteSegmentRequest(int playlistId, PublicSegment ps) {
        this.playlistId = playlistId;
        this.url = ps.url;
        this.characters = ps.character;
        this.text = ps.text;
    }

    public int getPlaylistId() {
        return this.playlistId;
    }

    public PublicSegment getPs() {
        return new PublicSegment(url, characters, text);
    }

    public void setPs(PublicSegment ps) {
        this.url = ps.url;
        this.characters = ps.character;
        this.text = ps.text;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }
}
