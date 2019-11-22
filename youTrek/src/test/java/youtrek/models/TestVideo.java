package youtrek.models;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import youtrek.models.Video;
import java.sql.Date;
import java.util.Calendar;

public class TestVideo {
    @Test
    public void testVideoEquals() {
        Video v1 = new Video(1, "test", "www.chekov.com", "ahhh", new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        Video v2 = new Video(2, "test2", "www.chekov.com", "ahhh", new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        assertTrue(v1.equals(v1));
        assertTrue(!v1.equals(v2));
    }
}
