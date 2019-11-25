package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.PlaylistDAO;
import youtrek.http.CreatePlaylistRequest;
import youtrek.http.CreatePlaylistResponse;
import youtrek.models.Playlist;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/* use case 1 */
public class CreatePlaylistHandler implements RequestHandler<CreatePlaylistRequest, CreatePlaylistResponse> {
    @Override
    public CreatePlaylistResponse handleRequest(CreatePlaylistRequest o, Context context) {
        Playlist pl;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try{
            pl = PlaylistDAO.getInstance().createPlaylist(o.getName());
            return new CreatePlaylistResponse(pl, headers, 200);
        } catch(SQLException e) {
            pl = new Playlist(-1, "N/A");
            return new CreatePlaylistResponse(pl, headers, 400);
        }
    }
}
