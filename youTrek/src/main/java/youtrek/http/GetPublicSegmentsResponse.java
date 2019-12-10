package youtrek.http;

import youtrek.models.ListOfPublicSegments;

import java.util.Map;


public class GetPublicSegmentsResponse extends AbstractYouTrekResponse<ListOfPublicSegments> {
    public GetPublicSegmentsResponse(ListOfPublicSegments body, Map<String, String> headers, int statusCode) {
        super(body, headers, statusCode);
    }
}
