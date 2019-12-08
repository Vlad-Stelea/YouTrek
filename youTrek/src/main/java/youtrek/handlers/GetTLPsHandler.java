package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.TlpDAO;
import youtrek.http.GetTLPsResponse;
import youtrek.http.GetTLPsRequest;
import youtrek.models.ListOfTlp;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/* use case 10/11 */
public class GetTLPsHandler implements RequestHandler<GetTLPsRequest, GetTLPsResponse> {
    @Override
    public GetTLPsResponse handleRequest(GetTLPsRequest request, Context context) {
        ListOfTlp lotlp;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
            try {
                lotlp = TlpDAO.getInstance().getAllTLP();
                return new GetTLPsResponse(lotlp, headers, 200);
            } catch (SQLException e) {
                lotlp = new ListOfTlp();
                return new GetTLPsResponse(lotlp, headers, 400);
            }

        }
    }

