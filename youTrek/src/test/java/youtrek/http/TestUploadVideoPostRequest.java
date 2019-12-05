package youtrek.http;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestUploadVideoPostRequest {
    UploadVideoPostRequest request;
    String name = "testName";
    String dialogue = "testDialogue";
    List<String> characters;
    //Base64 encoded string of the video's data
    String video = "testVideo";

    @Before
    public void setup() {
        characters = Arrays.asList("Spock", "Kirk", "Other Character");
        request = new UploadVideoPostRequest();
    }

    @Test
    public void testComplexConstructor() {
        request = new UploadVideoPostRequest(name, dialogue, characters, video);
        assertEquals(name, request.getName());
        assertEquals(dialogue, request.getDialogue());
        assertEquals(characters, request.getCharacters());
        assertEquals(video, request.getVideo());
    }

    @Test
    public void testSetName() {
        request.setName(name);
        assertEquals(name, request.getName());
    }

    @Test
    public void testSetDialogue() {
        request.setDialogue(dialogue);
        assertEquals(dialogue, request.getDialogue());
    }

    @Test
    public void testSetCharacters() {
        request.setCharacters(characters);
        assertEquals(characters, request.getCharacters());
    }

    @Test
    public void testSetVideo() {
        request.setVideo(video);
        assertEquals(video, request.getVideo());
    }
}
