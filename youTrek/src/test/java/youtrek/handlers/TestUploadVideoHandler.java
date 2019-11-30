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

    @Test
    public void testSplitKnownAndUnkownCharacters() {
        List<Character> databaseCharacters = Arrays.asList(
                new Character(1, "Spock"),
                new Character(2, "Kirk"),
                new Character(3, "Computer")
        );

        List<Character> requestChars = Arrays.asList(
                new Character("Kirk"),
                new Character("Dog"),
                new Character("Cat"),
                new Character("Human")
        );

        UploadVideoHandler.KnownUnknownCharacters kUC = handler.splitKnownAndUnknownCharacters(databaseCharacters, requestChars);

        List<Character> expectedKnown = Arrays.asList(
                new Character(2, "Kirk")
        );

        List<Character> expectedUnknown = Arrays.asList(
                new Character("Dog"),
                new Character("Cat"),
                new Character("Human")
        );

        assertEquals(expectedKnown, kUC.knownCharacters);

        assertEquals(expectedUnknown, kUC.unKnownCharacters);



    }
}
