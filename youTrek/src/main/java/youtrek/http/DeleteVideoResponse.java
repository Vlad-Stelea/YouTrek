package youtrek.http;

import youtrek.models.ListOfVideos;

import java.util.Map;

public class DeleteVideoResponse extends AbstractYouTrekResponse<ListOfVideos> {
    protected DeleteVideoResponse(ListOfVideos body, Map<String, String> headers, int statusCode) {
        super(body, headers, statusCode);
    }
}
