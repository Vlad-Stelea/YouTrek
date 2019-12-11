package youtrek.handlers;

import org.junit.Before;
import org.junit.Test;
import youtrek.db.DatabaseUtil;
import youtrek.db.TlpDAO;
import youtrek.http.DeleteTlpRequest;
import youtrek.http.DeleteTlpResponse;
import youtrek.models.ListOfTlp;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestDeleteTlpHandler {
    DeleteTlpHandler handler;
    ListOfTlp lov;
    DeleteTlpRequest request;

    @Before
    public void setup() throws SQLException {
        DatabaseUtil.setSchema("testing");
        TlpDAO dao = TlpDAO.getInstance();
        int id = dao.insertUrl("www.sometest.com");
        handler = new DeleteTlpHandler();
        lov = new ListOfTlp(new ArrayList<>());
        request = new DeleteTlpRequest();
        request.setId(id);
    }

    @Test
    public void testSuccessfulResponse() {
        DeleteTlpResponse result = handler.handleRequest(request, null);
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        assertNotNull(content);
    }
}
