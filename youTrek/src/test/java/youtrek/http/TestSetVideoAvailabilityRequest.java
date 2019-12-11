package youtrek.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSetVideoAvailabilityRequest {
    @Test
    public void TestComplexConstructor() {
        int videoId = 1;
        Boolean isAvail = true;
        SetVideoAvailabilityRequest request = new SetVideoAvailabilityRequest(videoId, isAvail);
        assertEquals(request.getIsAvail(), isAvail);
        assertEquals(request.getVideoId(), videoId);

    }
}
