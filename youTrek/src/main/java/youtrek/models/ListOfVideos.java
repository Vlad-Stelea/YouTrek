package youtrek.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListOfVideos implements Iterable<Video> {
    List<Video> videos;

    public ListOfVideos() {
        videos = new ArrayList<>();
    }

    public ListOfVideos(List<Video> videos) {
        this.videos = videos;
    }

    public void addVideo(Video video) {
        this.videos.add(video);
    }

    @Override
    public Iterator<Video> iterator() {
        return videos.iterator();
    }
}
