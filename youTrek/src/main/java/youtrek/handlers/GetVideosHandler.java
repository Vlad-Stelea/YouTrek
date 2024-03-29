package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.VideoDAO;
import youtrek.http.GetVideosRequest;
import youtrek.http.GetVideosResponse;
import youtrek.models.ListOfVideos;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/* use case 10/11 */
public class GetVideosHandler implements RequestHandler<GetVideosRequest, GetVideosResponse> {
    @Override
    public GetVideosResponse handleRequest(GetVideosRequest request, Context context) {
        ListOfVideos lov;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        if(request.hasFilter()) {
            try {
                ListOfVideos videoSegments = VideoDAO.getInstance().getVideoSegments(request.getFilter());
                return new GetVideosResponse(videoSegments, headers, 200);
            }catch (SQLException e) {
                lov = new ListOfVideos();
                return new GetVideosResponse(lov, headers, 400);
            }
        } else {
            try {
                ListOfVideos videoList = VideoDAO.getInstance().getVideoSegments();
                return new GetVideosResponse(videoList, headers, 200);
            } catch (SQLException e) {
                lov = new ListOfVideos();
                return new GetVideosResponse(lov, headers, 400);
            }

        }
    }
}
