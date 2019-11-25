package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.PlaylistDAO;
import youtrek.http.AppendVideoRequest;
import youtrek.http.AppendVideoResponse;
import youtrek.http.CreatePlaylistRequest;
import youtrek.http.CreatePlaylistResponse;
import youtrek.models.Playlist;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/* use case 8 */
public class AppendVideoHandler implements RequestHandler<AppendVideoRequest, AppendVideoResponse> {
    @Override
    public AppendVideoResponse handleRequest(AppendVideoRequest o, Context context) {
        Playlist pl;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try{
            pl = PlaylistDAO.getInstance().appendVideo(o.getVideoId(), o.getPlaylistId());
            return new AppendVideoResponse(pl, headers, 200);
        } catch(SQLException e) {
            pl = new Playlist(-1, "N/A");
            return new AppendVideoResponse(pl, headers, 400);
        }
    }
}
