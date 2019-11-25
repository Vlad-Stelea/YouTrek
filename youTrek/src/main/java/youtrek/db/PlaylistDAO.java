package youtrek.db;

import youtrek.models.ListOfPlaylists;
import youtrek.models.ListOfVideos;
import youtrek.models.Playlist;
import youtrek.models.Video;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Playlist createPlaylist(String name) throws SQLException {
        try {
            /* create playlist */
            Playlist p1 = null;
            int insert_id = -1;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO playlists (NAME) VALUES (?);", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            int rcode = ps.executeUpdate();
            /* query for playlist just inserted */
            ResultSet rset = ps.getGeneratedKeys();
            if (rset.next()) {
                insert_id = rset.getInt(1);
            }
            if (insert_id != -1) {
                return getPlaylist(insert_id);
            }
            else {
                /* figure out how we want to do this error handling */
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in creating playlist: " + e.getMessage());
        }
    }

    public Playlist getPlaylist(int playlist_id) throws SQLException {
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
            throw new SQLException("Failed in getting playlist: " + e.getMessage());
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
                currentPlaylist.setVideos(getPlayListVideos(currentPlaylist.id));
                playlists.appendPlaylist(currentPlaylist);
            }
            resultSet.close();
            ps.close();

            return playlists;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in getting list of playlists: " + e.getMessage());
        }
    }

    public ListOfVideos getPlayListVideos(int playlist_id) throws SQLException {
        try {
            ListOfVideos videoSegments = new ListOfVideos();
            Video currentVideo = null;
            PreparedStatement ps = conn.prepareStatement("select * from videos join pvjoin on videos.id=pvjoin.video_id where playlist_id=? order by video_order;");
            ps.setInt(1, playlist_id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                currentVideo = generateVideo(resultSet);
                videoSegments.appendVideo(currentVideo);
            }
            resultSet.close();
            ps.close();

            return videoSegments;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in getting playlist videos: " + e.getMessage());
        }
    }

    public void appendVideo(int video_id, int playlist_id) throws SQLException {
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
            throw new SQLException("Failed in adding video to playlist: " + e.getMessage());
        }
    }

    private Playlist generatePlaylist(ResultSet rset) throws Exception {
        int id = rset.getInt("id");
        String name = rset.getString("name");
        Playlist pl = new Playlist(id,name);
        return pl;
    }

    //TODO clean
    public Video generateVideo(ResultSet rset) throws Exception {
        int id = rset.getInt("videos.id");
        String name = rset.getString("videos.name");
        String url = rset.getString("url");
        String dialogue = rset.getString("dialogue");
        Date dateCreated = rset.getDate("date_created");
        int tlpId = rset.getInt("tlp_id");
        boolean isRemote = rset.getBoolean("is_remote");
        boolean isAvailable = rset.getBoolean("is_available");
        Video video = new Video(id,name, url, dialogue, dateCreated, tlpId, isRemote, isAvailable);
        return video;
    }
}
