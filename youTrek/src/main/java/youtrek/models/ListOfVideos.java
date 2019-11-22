package youtrek.models;

import java.util.ArrayList;
import java.util.List;

public class ListOfVideos {
    List<Video> videos;

    public ListOfVideos() {
        videos = new ArrayList<>();
    }

    public ListOfVideos(List<Video> videos) {
        this.videos = videos;
    }
}
