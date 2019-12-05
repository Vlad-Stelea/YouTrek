package youtrek.db;

import org.junit.Before;
import org.junit.Test;
import youtrek.models.Character;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

public class TestCharacterDao {


    @Before
    public void setup() {
        DatabaseUtil.setSchema("testing");
    }

    @Test
    public void testGetAllCharacters() throws SQLException {
        List<Character> expectedCharacters = Arrays.asList(
                new Character(1,"Chekov"),
                new Character(2,"Spock"),
                new Character(3,"Kirk"),
                new Character(4,"Uhura"),
                new Character(5,"Scotty"),
                new Character(6,"Bones"),
                new Character(7,"Sulu")
        );
        Set<Character> returnedCharacterSet = new HashSet<>(CharacterDAO.getInstance().getCharacters());
        assertTrue(returnedCharacterSet.containsAll(expectedCharacters));
    }

    @Test
    public void testInsertCharacters() throws SQLException {
        int spockId = 2;
        List<Character> toInsert = Arrays.asList(
                new Character("Test1"),
                new Character("Spock"),
                new Character("Test2")
        );
        List<Integer> characterIds = CharacterDAO.getInstance().insertCharacters(toInsert);
        assertTrue(characterIds.contains(spockId));
        assertTrue(characterIds.size() == toInsert.size());

        Set<Character> allCharactersInTable = new HashSet<>(
                CharacterDAO.getInstance().getCharacters()
        );

        assertTrue(allCharactersInTable.containsAll(toInsert));
        //TODO delete inserted characters
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
