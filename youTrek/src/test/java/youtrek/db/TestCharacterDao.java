package youtrek.db;

import org.junit.Test;
import youtrek.models.Character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TestCharacterDao {


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

       CharacterDAO.KnownUnknownCharacters kUC = CharacterDAO.getInstance().splitKnownAndUnknownCharacters(databaseCharacters, requestChars);

        List<Character> expectedKnown = Arrays.asList(
                new Character(2, "Kirk")
        );

        List<Character> expectedUnknown = Arrays.asList(
                new Character("Dog"),
                new Character("Cat"),
                new Character("Human")
        );

        assertEquals(expectedKnown, new ArrayList<>(kUC.knownCharacters.values()));

        assertEquals(expectedUnknown, kUC.unKnownCharacters);
    }
}
