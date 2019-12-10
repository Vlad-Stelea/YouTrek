package youtrek.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestListOfPublicSegment {
    @Test
    public void testConstructorEquals() {
        // Create List of Public Segments via video conversion
        List<String> characters = new ArrayList<>();
        characters.add("Chekov");
        characters.add("Spock");
        Video v = new Video("www.google.com", characters, "ahh");
        ListOfVideos lov = new ListOfVideos();
        lov.appendVideo(v);
        ListOfPublicSegments segments1 = new ListOfPublicSegments(lov);
        // Create List of Public Segments with corresponding video
        ListOfPublicSegments segments2 = new ListOfPublicSegments();
        PublicSegment constructedSegment = new PublicSegment("www.google.com", "Chekov,Spock", "ahh");
        segments2.appendSegment(constructedSegment);
        // Check Equals
        for (PublicSegment ps1 : segments1) {
            for (PublicSegment ps2 : segments2) {
                assertEquals(ps1, ps2);
            }
        }
    }
}
