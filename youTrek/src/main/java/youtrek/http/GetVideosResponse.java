package youtrek.http;

import youtrek.models.ListOfVideos;

import java.util.Map;

public class GetVideosResponse extends AbstractYouTrekResponse<ListOfVideos> {

    public GetVideosResponse(ListOfVideos videos, final Map<String, String> headers, final int statusCode) {
        super(videos, headers, statusCode);
    }

}
