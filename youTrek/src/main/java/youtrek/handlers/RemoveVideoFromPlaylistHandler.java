package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import youtrek.db.PlaylistDAO;
import youtrek.http.RemoveVideoFromPlaylistRequest;
import youtrek.http.RemoveVideoFromPlaylistResponse;
import youtrek.models.Playlist;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/* use case 9 */
public class RemoveVideoFromPlaylistHandler implements RequestHandler<RemoveVideoFromPlaylistRequest, RemoveVideoFromPlaylistResponse> {
    @Override
    public RemoveVideoFromPlaylistResponse handleRequest(RemoveVideoFromPlaylistRequest o, Context context) {
        Playlist pl;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try{
            pl = PlaylistDAO.getInstance().removeVideoFromPlaylist(o.getVideoId(), o.getPlaylistId());
            return new RemoveVideoFromPlaylistResponse(pl, headers, 200);
        } catch(SQLException e) {
            pl = new Playlist(-1, "N/A");
            return new RemoveVideoFromPlaylistResponse(pl, headers, 400);
        }
    }
}
