package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.VideoDAO;
import youtrek.http.GetPublicSegmentsRequest;
import youtrek.http.GetPublicSegmentsResponse;
import youtrek.models.ListOfPublicSegments;
import youtrek.models.ListOfVideos;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/* public endpoint for our video segments */
public class GetPublicSegmentsHandler implements RequestHandler<GetPublicSegmentsRequest, GetPublicSegmentsResponse> {
    @Override
    public GetPublicSegmentsResponse handleRequest(GetPublicSegmentsRequest request, Context context) {
        ListOfPublicSegments segments;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try {
            ListOfVideos lov = VideoDAO.getInstance().getPublicSegments();
            segments = new ListOfPublicSegments(lov); // convert lov to public segments format
            return new GetPublicSegmentsResponse(segments, headers, 200);
        }catch (SQLException e) {
            segments = new ListOfPublicSegments();
            return new GetPublicSegmentsResponse(segments, headers, 400);
        }
    }
}


/*
{ "segments" :
  [
    { "url"        : "s3-bucket-url-1",
      "character"  : "char-1",
      "text"       : "text-1"
    },
    { "url"        : "s3-bucket-url-2",
      "character"  : "char-2",
      "text"       : "text-2"
    }
  ]
}
 */