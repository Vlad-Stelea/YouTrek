package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.PlaylistDAO;

import youtrek.http.ListPlaylistsRequest;
import youtrek.http.ListPlaylistsResponse;
import youtrek.models.ListOfPlaylists;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ListPlaylistsHandler implements RequestHandler<ListPlaylistsRequest, ListPlaylistsResponse> {
    @Override
    public ListPlaylistsResponse handleRequest(ListPlaylistsRequest o, Context context) {
        ListOfPlaylists lop;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try{
            lop = PlaylistDAO.getInstance().listPlaylists();
            return new ListPlaylistsResponse(lop, headers, 200);
        } catch(SQLException e) {
            lop = new ListOfPlaylists();
            return new ListPlaylistsResponse(lop, headers, 400);
        }
    }
}
