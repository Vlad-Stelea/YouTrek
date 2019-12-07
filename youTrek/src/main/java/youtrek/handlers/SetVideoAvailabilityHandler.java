package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.VideoDAO;
import youtrek.http.SetVideoAvailabilityRequest;
import youtrek.http.SetVideoAvailabilityResponse;
import youtrek.models.Video;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SetVideoAvailabilityHandler implements RequestHandler<SetVideoAvailabilityRequest, SetVideoAvailabilityResponse> {
    @Override
    public SetVideoAvailabilityResponse handleRequest(SetVideoAvailabilityRequest o, Context context) {
        Video v;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try{
            v = VideoDAO.getInstance().setVideoAvailability(o.getVideoId(), o.getIsAvail());
            return new SetVideoAvailabilityResponse(v, headers, 200);
        } catch(SQLException e) {
            v = new Video("N/A", "N/A", "N/A");
            return new SetVideoAvailabilityResponse(v, headers, 400);
        }
    }
}
