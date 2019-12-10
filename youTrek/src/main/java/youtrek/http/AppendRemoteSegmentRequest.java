package youtrek.http;

import com.google.gson.GsonBuilder;
import youtrek.models.PublicSegment;

public class AppendRemoteSegmentRequest {
    public int playlistId;
    public String url;
    public String characters;
    public String text;
    // PublicSegment ps;

    public AppendRemoteSegmentRequest() {

    }
    public AppendRemoteSegmentRequest(int playlistId, String url, String characters, String text) {
        this.playlistId = playlistId;
        this.url = url;
        this.characters = characters;
        this.text = text;
    }

    public PublicSegment getPs() {
        return new PublicSegment(url, characters, text);
    }

    public int getPlaylistId() {
        return this.playlistId;
    }


    public String getUrl() {
        return this.url;
    }

    public String getText() {
        return this.text;
    }

    public String getCharacters() {
        return this.characters;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }
}
