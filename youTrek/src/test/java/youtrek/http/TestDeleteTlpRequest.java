package youtrek.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDeleteTlpRequest {
    @Test
    public void TestComplexConstructor() {
        int id = 1;
        DeleteTlpRequest request = new DeleteTlpRequest(id);
        assertEquals(id, request.getId());
    }
}
