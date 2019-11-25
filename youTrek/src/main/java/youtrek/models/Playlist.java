package youtrek.models;

public class Playlist {
    public final int id;
    public final String name;
    public ListOfVideos videos;

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
        this.videos = new ListOfVideos();
    }

    public void appendVideo(Video video) {
        videos.appendVideo((video));
    }

    public void setVideos(ListOfVideos videos) {
        this.videos = videos;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        else if (o instanceof Playlist) {
            Playlist pl = (Playlist) o;
            return pl.id == this.id;
        }
        return false;
    }
}
