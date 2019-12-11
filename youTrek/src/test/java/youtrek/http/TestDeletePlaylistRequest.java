package youtrek.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDeletePlaylistRequest {
    @Test
    public void TestComplexConstructor() {
        int id = 1;
        DeletePlaylistRequest request = new DeletePlaylistRequest(id);
        assertEquals(id, request.getId());
    }
}
