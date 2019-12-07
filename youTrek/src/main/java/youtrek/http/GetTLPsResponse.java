package youtrek.http;

import youtrek.models.ListOfTlp;
import youtrek.models.RegisterTlpResponseBody;

import java.util.Map;

public class GetTLPsResponse extends AbstractYouTrekResponse<ListOfTlp>{

    public GetTLPsResponse(ListOfTlp body, Map<String, String> headers, int statusCode) {
        super(body, headers, statusCode);
    }
}
