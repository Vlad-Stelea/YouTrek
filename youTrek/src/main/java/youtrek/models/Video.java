package youtrek.models;

import java.sql.Date; //TODO make sure that we're using java.sql.Date throughout the project (NOT java.util.Date)

public class Video {
    public final int id;
    public final String name;
    public final String url;
    public final String dialogue;
    public final Date dateCreated;
    public int tlpId; //TODO check if this should be marked final
    public boolean isRemote;
    public boolean isAvailable;

    //TODO check if we ever need anything other than full constructor
    public Video(int id, String name, String url, String dialogue, Date dateCreated) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.dialogue = dialogue;
        this.dateCreated = dateCreated;
        tlpId = -1;
        isRemote = false;
        isAvailable = false;
    }

    public Video(int id, String name, String url, String dialogue, Date dateCreated, int tlpId, boolean isRemote, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.dialogue = dialogue;
        this.dateCreated = dateCreated;
        this.tlpId = tlpId;
        this.isRemote = isRemote;
        this.isAvailable = isAvailable;
    }

    public void setIsRemote(boolean b) {
        this.isRemote = b;
    }
    public void setIsAvailable(boolean b) {
        this.isAvailable = b;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        else if (o instanceof Video) {
            Video v = (Video) o;
            return v.id == this.id;
        }
        return false;
    }

}
