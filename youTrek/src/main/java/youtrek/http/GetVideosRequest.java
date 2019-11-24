package youtrek.http;

import com.google.gson.GsonBuilder;

public class GetVideosRequest {
    String filter;

    public GetVideosRequest() {
        //DO Nothing
    }

    public GetVideosRequest(String filter) {
        this.filter = filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    public boolean hasFilter() {
        return !(this.filter == null);
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }
}
