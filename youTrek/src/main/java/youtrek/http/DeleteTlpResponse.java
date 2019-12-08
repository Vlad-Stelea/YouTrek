package youtrek.http;

import youtrek.models.ListOfTlp;

import java.util.Map;

public class DeleteTlpResponse extends AbstractYouTrekResponse<ListOfTlp> {
    public DeleteTlpResponse(ListOfTlp body, Map<String, String> headers, int statusCode) {
        super(body, headers, statusCode);
    }
}
