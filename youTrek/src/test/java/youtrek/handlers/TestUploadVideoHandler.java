package youtrek.handlers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import youtrek.models.Character;

import java.util.Arrays;
import java.util.List;

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

}
