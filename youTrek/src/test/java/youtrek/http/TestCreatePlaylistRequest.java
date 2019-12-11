package youtrek.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCreatePlaylistRequest {
    @Test
    public void TestComplexConstructor() {
        String name = "ChekovPlaylist";
        CreatePlaylistRequest request = new CreatePlaylistRequest(name);
        assertEquals(name, request.getName());
    }
}
