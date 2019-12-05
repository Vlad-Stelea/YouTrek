package youtrek.http;

import youtrek.models.Video;

import java.util.Map;

public class UploadVideoResponse extends AbstractYouTrekResponse<Video> {

    public UploadVideoResponse(Video body, Map<String, String> headers, int statusCode) {
        super(body, headers, statusCode);
    }

}
