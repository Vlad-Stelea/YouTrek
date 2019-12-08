package youtrek.http;

import com.google.gson.GsonBuilder;

public class SetVideoAvailabilityRequest {
    int videoId;
    Boolean isAvail;

    public SetVideoAvailabilityRequest() {

    }
    public SetVideoAvailabilityRequest(int videoId, Boolean isAvail) {
        this.videoId = videoId;
        this.isAvail = isAvail;
    }

    public int getVideoId() {
        return this.videoId;
    }
    public Boolean getIsAvail() {
        return this.isAvail;
    }

    public void setVideoId(int id) {
        this.videoId = id;
    }
    public void setIsAvail(Boolean isAvail) {
        this.isAvail = isAvail;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }

}
