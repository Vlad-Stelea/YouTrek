package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.PlaylistDAO;
import youtrek.http.DeletePlaylistRequest;
import youtrek.http.DeletePlaylistResponse;
import youtrek.models.ListOfPlaylists;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/* use case 3 */
public class DeletePlaylistHandler implements RequestHandler<DeletePlaylistRequest, DeletePlaylistResponse> {
    @Override
    public DeletePlaylistResponse handleRequest(DeletePlaylistRequest o, Context context) {
        ListOfPlaylists playlists;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try{
            playlists = PlaylistDAO.getInstance().deletePlaylist(o.getId());
            return new DeletePlaylistResponse(playlists, headers, 200);
        } catch(SQLException e) {
            playlists = new ListOfPlaylists();
            return new DeletePlaylistResponse(playlists, headers, 400);
        }
    }
}
