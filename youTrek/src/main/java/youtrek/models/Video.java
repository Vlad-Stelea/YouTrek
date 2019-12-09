package youtrek.models;

import java.sql.Date; //TODO make sure that we're using java.sql.Date throughout the project (NOT java.util.Date)
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class Video {
    public int id;
    public final String name;
    public final String url;
    public final String dialogue;
    public  Date dateCreated;
    public int tlpId;
    public boolean isRemote;
    public boolean isAvailable;
    public List<String> characters;

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

    public Video(String url, List<String> characters, String dialogue) {
        this.name = null;
        this.url = url;
        this.characters = characters;
        this.dialogue = dialogue;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (o instanceof Video) {
            Video v = (Video) o;
            return  v.name.equals(this.name)&&
                    v.dialogue.equals(this.dialogue) &&
                    v.url.equals(this.url);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);
        fmt.format("|ID:%d  Name: %s|", id, name);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
