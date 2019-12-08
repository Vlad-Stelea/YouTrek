package youtrek.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestRegisterTlpResponseBody {

    @Test
    public void testRegisterTlpResponseBodyInitializedCorrectly() {
        int id = 30;
        String url = "www.youtrek.com";
        RegisterTlpResponseBody body = new RegisterTlpResponseBody(id, url);
        assertEquals(id, body.id);
        assertEquals(url, body.url);
    }
}
