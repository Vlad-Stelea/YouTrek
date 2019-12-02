package youtrek.models;

import java.sql.Date; //TODO make sure that we're using java.sql.Date throughout the project (NOT java.util.Date)
import java.util.ArrayList;
import java.util.List;

public class Video {
    public int id;
    public final String name;
    public final String url;
    public final String dialogue;
    public  Date dateCreated;
    public int tlpId; //TODO check if this should be marked final
    public boolean isRemote;
    public boolean isAvailable;
    public List<String> characters;

    //TODO check if we ever need anything other than full constructor
    public Video(int id, String name, String url, String dialogue, Date dateCreated) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.dialogue = dialogue;
        this.dateCreated = dateCreated;
        this.characters = new ArrayList<>();
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
        this.characters = new ArrayList<>();
    }

    public Video(String name, String url, String dialogue) {
        this.name = name;
        this.url = url;
        this.dialogue = dialogue;
        this.tlpId = -1;
        this.isRemote = false;
        this.isAvailable = true;
    }

    public void setIsRemote(boolean b) {
        this.isRemote = b;
    }
    public void setIsAvailable(boolean b) {
        this.isAvailable = b;
    }

    public void addCharacters(List<String> characters) {
        this.characters.addAll(characters);
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
