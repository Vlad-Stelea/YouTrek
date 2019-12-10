package youtrek.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestPublicSegment {
    @Test
    public void testConstructorEquals() {
        List<String> characters = new ArrayList<>();
        characters.add("Chekov");
        characters.add("Spock");
        Video v = new Video("www.google.com", characters, "ahh");
        PublicSegment constructedSegment = new PublicSegment("www.google.com", "Chekov,Spock", "ahh");
        PublicSegment convertedSegment = new PublicSegment(v);
        assertEquals(constructedSegment, convertedSegment);
    }
}
