package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.TlpDAO;
import youtrek.http.RegisterTlpRequest;
import youtrek.http.RegisterTlpResponse;
import youtrek.models.RegisterTlpResponseBody;

import java.util.HashMap;
import java.util.Map;

public class RegisterTlpHandler implements RequestHandler<RegisterTlpRequest, RegisterTlpResponse> {
    @Override
    public RegisterTlpResponse handleRequest(RegisterTlpRequest registerTlpRequest, Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        try{
            String url = registerTlpRequest.getUrl();
            int insertedId = TlpDAO.getInstance().insertUrl(url);
            RegisterTlpResponseBody body = new RegisterTlpResponseBody(insertedId, url);
            return new RegisterTlpResponse(body, headers, 200);
        } catch (Exception e) {
            return new RegisterTlpResponse(null, headers, 400);
        }
    }
}
