package youtrek.http;

import java.util.List;

public class UploadVideoPostRequest {
    String name;
    String dialogue;
    List<String> characters;
    //Base64 encoded string of the video's data
    String video;

    public UploadVideoPostRequest() {
        //Do nothing
    }

    public UploadVideoPostRequest(String name, String dialogue, List<String> characters, String video) {
        this.name = name;
        this.dialogue = dialogue;
        this.characters = characters;
        this.video = video;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialogue() {
        return this.dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public List<String> getCharacters() {
        return this.characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public String getVideo() {
        return this.video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
