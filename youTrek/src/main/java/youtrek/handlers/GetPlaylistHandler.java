package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.PlaylistDAO;
import youtrek.http.*;
import youtrek.models.Playlist;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/* use case 4 */
public class GetPlaylistHandler implements RequestHandler<GetPlaylistRequest, GetPlaylistResponse> {
    @Override
    public GetPlaylistResponse handleRequest(GetPlaylistRequest o, Context context) {
        Playlist pl;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try{
            pl = PlaylistDAO.getInstance().getPlaylist(o.getId());
            pl.setVideos(PlaylistDAO.getInstance().getPlayListVideos(o.getId()));
            return new GetPlaylistResponse(pl, headers, 200);
        } catch(SQLException e) {
            pl = new Playlist(-1, "N/A");
            return new GetPlaylistResponse(pl, headers, 400);
        }
    }
}
