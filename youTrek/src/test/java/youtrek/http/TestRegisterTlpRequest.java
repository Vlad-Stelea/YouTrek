package youtrek.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestRegisterTlpRequest {
    @Test
    public void TestComplexConstructor() {
        String url = "www.google.com";
        RegisterTlpRequest request1 = new RegisterTlpRequest();
        request1.setUrl(url);
        RegisterTlpRequest request2 = new RegisterTlpRequest(url);
        assertEquals(request1.getUrl(), request2.getUrl());
    }
}
