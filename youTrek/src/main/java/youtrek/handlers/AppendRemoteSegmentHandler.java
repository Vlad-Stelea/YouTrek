package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.PlaylistDAO;
import youtrek.http.AppendRemoteSegmentRequest;
import youtrek.http.AppendRemoteSegmentResponse;
import youtrek.models.Playlist;
import youtrek.models.PublicSegment;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AppendRemoteSegmentHandler implements RequestHandler<AppendRemoteSegmentRequest, AppendRemoteSegmentResponse> {
    @Override
    public AppendRemoteSegmentResponse handleRequest(AppendRemoteSegmentRequest o, Context context) {
        Playlist pl;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try{
            PublicSegment ps = new PublicSegment(o.getUrl(), o.getCharacters(), o.getText());
            pl = PlaylistDAO.getInstance().appendRemoteSegment(o.getPs(), o.getPlaylistId());
            return new AppendRemoteSegmentResponse(pl, headers, 200);
        } catch(SQLException e) {
            pl = new Playlist(-1, "N/A");
            return new AppendRemoteSegmentResponse(pl, headers, 400);
        }
    }
}
