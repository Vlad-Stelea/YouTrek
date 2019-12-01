package youtrek.models;

import static org.junit.Assert.*;

import org.junit.Test;
import java.sql.Date;
import java.util.Calendar;

public class TestVideo {
    @Test
    public void testVideoEquals() {
        Video v1 = new Video(1, "test", "www.chekov.com", "ahhh", new Date(Calendar.getInstance().getTime().getTime()));
        Video v2 = new Video(2, "test2", "www.chekov.com", "ahhh", new Date(Calendar.getInstance().getTime().getTime()));
        assertTrue(v1.equals(v1));
        assertTrue(!v1.equals(v2));
    }

    @Test
    public void testSetIsRemote() {
        Video v1 = new Video("Test name 1", "www.test.com", "Test dialogue");
        assertFalse(v1.isRemote);
        v1.setIsRemote(true);
        assertTrue(v1.isRemote);
    }

    @Test
    public void testSetIsAvailable() {
        Video v1 = new Video("Test name 1", "www.test.com", "Test dialogue");
        assertTrue(v1.isAvailable);
        v1.setIsAvailable(false);
        assertFalse(v1.isAvailable);
    }
}
