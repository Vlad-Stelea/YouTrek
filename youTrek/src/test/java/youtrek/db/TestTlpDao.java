package youtrek.db;

import org.junit.Before;
import org.junit.Test;
import youtrek.models.ListOfTlp;

import static org.junit.Assert.*;

import java.sql.SQLException;

public class TestTlpDao {
    TlpDAO dao;

    @Before
    public void setup() {
        DatabaseUtil.setSchema("testing");
        dao = TlpDAO.getInstance();
    }

    @Test
    public void testInsertUrl() throws SQLException {
        String url = "www.testurl.com";
        int id = dao.insertUrl(url);
        String urlFromId = dao.getUrlById(id);
        assertEquals(url, urlFromId);
        //TODO delete url
    }

    @Test
    public void testGetUrlById() throws SQLException{
        int id = 1;
        String expectedUrl = "https://xscratch-videos.s3.us-east-2.amazonaws.com";
        String fetchedUrl = dao.getUrlById(id);
        assertEquals(expectedUrl, fetchedUrl);
    }

    @Test
    public void testGetAllTLP() throws SQLException{
        TlpDAO dao = TlpDAO.getInstance();
        ListOfTlp tlpList = dao.getAllTLP();
        assertNotNull(tlpList);
    }

}
