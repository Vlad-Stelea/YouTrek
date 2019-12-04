package youtrek.handlers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import youtrek.http.UploadVideoPostRequest;
import youtrek.http.UploadVideoResponse;
import youtrek.models.Character;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestUploadVideoHandler {
    UploadVideoHandler handler;

    @Before
    public void setup() {
        handler = new UploadVideoHandler();
    }

    @Test
    public void testConvertNamesToCharacters() {
        List<String> characterNames = Arrays.asList(
                "Spock",
                "Kirk",
                "Computer"
        );

        List<Character> expectedCharacters = Arrays.asList(
                new Character("Spock"),
                new Character("Kirk"),
                new Character("Computer")
        );

        List<Character> characters = handler.convertNamesToCharacters(characterNames);

        assertEquals(expectedCharacters, characters);
    }

    @Test
    public void testUploadVideoHandlerHasCorrectHeaders() {
        String name = "Test Video";
        String dialogue = "Test dialogue";
        List<String> characters = Arrays.asList(
                "Spock",
                "Kirk"
        );
        String video = "Test video";
        UploadVideoPostRequest request = new UploadVideoPostRequest(name, dialogue, characters, video);
        UploadVideoResponse response = handler.handleRequest(request, null);
        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.getHeaders().get("Content-Type"), "application/json");
        String content = response.getBody();
        assertNotNull(content);
        //TODO delete inserted video
    }
}
