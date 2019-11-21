package youtrek.db;

import youtrek.models.Playlist;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    //TODO finish/check
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
