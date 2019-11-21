package youtrek.db;

import youtrek.models.ListOfPlaylists;
import youtrek.models.Playlist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO {

    java.sql.Connection conn;
    private static PlaylistDAO instance = null;
    public PlaylistDAO() {
        try  {
            conn = DatabaseUtil.connect();
        } catch (Exception e) {
            conn = null;
        }
    }

    public static PlaylistDAO getInstance() {
        if(instance == null) instance = new PlaylistDAO();
        return instance;
    }


    public Playlist getPlaylist(int playlist_id) throws Exception {
        try {
            Playlist pl = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM playlists WHERE id=?;");
            ps.setInt(1,  playlist_id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                pl = generatePlaylist(resultSet);
            }
            resultSet.close();
            ps.close();

            return pl;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed in getting playlist: " + e.getMessage());
        }
    }

    public ListOfPlaylists listPlaylists() throws SQLException {
        try {
            ListOfPlaylists playlists = new ListOfPlaylists();
            Playlist currentPlaylist = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM playlists;");
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                currentPlaylist = generatePlaylist(resultSet);
                playlists.appendPlaylist(currentPlaylist);
            }
            resultSet.close();
            ps.close();

            return playlists;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in getting list of videos: " + e.getMessage());
        }
    }

    //TODO finish & check
    public void appendVideo(int video_id, int playlist_id) throws Exception {
        try {
            Playlist pl = null;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO pvjoin (video_id, playlist_id) values (?, ?);");
            ps.setInt(1, video_id);
            ps.setInt(2, playlist_id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                pl = generatePlaylist(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed in adding video to playlist: " + e.getMessage());
        }
    }

    private Playlist generatePlaylist(ResultSet rset) throws Exception {
        int id = rset.getInt("id");
        String name = rset.getString("name");
        Playlist pl = new Playlist(id,name);
        return pl;
    }
}
