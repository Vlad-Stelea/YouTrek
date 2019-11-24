package youtrek.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import youtrek.models.Video;
import java.util.ArrayList;

public class TestDatabaseUtil {
    @Test
    public void testConnection () throws Exception {
        java.sql.Connection conn;
        conn = DatabaseUtil.connect();
        assertNotNull(conn);
    }
}
