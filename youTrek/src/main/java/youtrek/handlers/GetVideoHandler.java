package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.VideoDAO;
import youtrek.http.GetVideosRequest;
import youtrek.http.GetVideosResponse;
import youtrek.models.ListOfVideos;
import youtrek.models.Video;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetVideoHandler implements RequestHandler<GetVideosRequest, GetVideosResponse> {
    @Override
    public GetVideosResponse handleRequest(GetVideosRequest o, Context context) {
        ListOfVideos lov;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try{
            lov = VideoDAO.getInstance().getVideoSegments();
            return new GetVideosResponse(lov, headers, 200);
        } catch(SQLException e) {
            lov = new ListOfVideos();
            return new GetVideosResponse(lov, headers, 400);
        }
    }
}
