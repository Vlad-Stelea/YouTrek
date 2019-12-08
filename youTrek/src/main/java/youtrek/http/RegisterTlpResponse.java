package youtrek.http;

import youtrek.models.RegisterTlpResponseBody;

import java.util.Map;

public class RegisterTlpResponse extends AbstractYouTrekResponse<RegisterTlpResponseBody>{

    public RegisterTlpResponse(RegisterTlpResponseBody body, Map<String, String> headers, int statusCode) {
        super(body, headers, statusCode);
    }
}
