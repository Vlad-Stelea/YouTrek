package youtrek.http;

import youtrek.models.Video;

import java.util.Map;

public class SetVideoAvailabilityResponse extends AbstractYouTrekResponse<Video> {
    public SetVideoAvailabilityResponse(Video body, Map<String, String> headers, int statusCode) {
        super(body, headers, statusCode);
    }
}
