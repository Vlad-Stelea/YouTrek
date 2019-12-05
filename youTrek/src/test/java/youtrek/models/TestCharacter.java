package youtrek.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestCharacter {

    @Before
    public void setup() {

    }

    @Test
    public void testEquals() {
        Character spock1 = new Character("Spock");
        Character spock2 = new Character(1, "Spock");
        Character notSpock = new Character("Not spock");

        assertEquals(spock1,spock2);
        assertNotEquals(spock1, notSpock);
        assertNotEquals(spock2, notSpock);
    }

    @Test
    public void testToString() {
        String name = "Spock";
        Character spock1 = new Character(name);

        assertEquals(name, spock1.toString());
    }
}
