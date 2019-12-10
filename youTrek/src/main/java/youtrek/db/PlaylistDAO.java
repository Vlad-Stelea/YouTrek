package youtrek.db;

import youtrek.models.*;
import youtrek.models.Character;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.CREATE_PLAYLIST_GIVEN_NAME, PreparedStatement.RETURN_GENERATED_KEYS);
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

    public ListOfPlaylists deletePlaylist(int playlist_id) throws SQLException {
        try {
            /* delete playlist */
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.DELETE_PLAYLIST_GIVEN_ID);
            ps.setInt(1, playlist_id);
            int ret = ps.executeUpdate();

            /* return list of playlist after deletion */
            return listPlaylists();

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in deleting playlist: " + e.getMessage());
        }
    }

    public Playlist getPlaylist(int playlist_id) throws SQLException {
        try {
            Playlist pl = null;
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.GET_PLAYLIST_GIVEN_ID);
            ps.setInt(1,  playlist_id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                pl = generatePlaylist(resultSet);
            }
            resultSet.close();
            ps.close();

            pl.setVideos(getPlaylistVideos(playlist_id));

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
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.GET_ALL_PLAYLISTS);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                currentPlaylist = generatePlaylist(resultSet);
                currentPlaylist.setVideos(getPlaylistVideos(currentPlaylist.id));
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

    // Useful especially for cleaning up after junit tests
    public void deletePlaylistByName(String name) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.DELETE_PLAYLIST_GIVEN_NAME);
            ps.setString(1, name);
            int ret = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in deleting playlist: " + e.getMessage());
        }
    }

    public ListOfVideos getPlaylistVideos(int playlist_id) throws SQLException {
        try {
            ListOfVideos videoSegments = new ListOfVideos();
            Video currentVideo = null;
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.GET_ALL_VIDEOS_FROM_PLAYLIST);
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

    // helper to find the current number of video segments in playlist
    private int getCurrentPlaylistIndex(int playlist_id) throws SQLException {
        try {
            int videoCount = 0;
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.GET_MAX_VIDEO_ORDER_GIVEN_PLAYLIST_ID);
            ps.setInt(1, playlist_id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                videoCount = resultSet.getInt(1);
            }

            return videoCount;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in adding video to playlist: " + e.getMessage());
        }

    }

    public Playlist appendRemoteSegment(PublicSegment ps, int playlist_id) throws SQLException {
        try {
            // convert public segment object to video, insert into table
            // insert converted video into pvjoin, return playlist
            VideoDAO vDAO = VideoDAO.getInstance();
            CharacterDAO cDAO = CharacterDAO.getInstance();
            Video convertedVideo = new Video(ps);
            int insertId = vDAO.createVideo(convertedVideo);
            // find character ids for their characters
            List<Character> characterList = new ArrayList<>();
            for (String s : convertedVideo.characters) {
                // add character ids to list for vcjoin insert, and if character not in DAO, add them to our DB
                Character c = new Character(s);
                characterList.add(c);
            }
            List<Integer> characterMapIds = cDAO.insertCharacters(characterList);
            vDAO.insertVideoCharactersPair(insertId, characterMapIds);
            return appendVideo(insertId, playlist_id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in adding remote video to playlist: " + e.getMessage());
        }
    }

    public Playlist appendVideo(int video_id, int playlist_id) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.APPEND_VIDEO_TO_PLAYLIST_GIVEN_IDS);
            ps.setInt(1, video_id);
            ps.setInt(2, playlist_id);
            ps.setInt(3, getCurrentPlaylistIndex(playlist_id)+1); // next video order value
            conn.setAutoCommit(false);
            int rcode = ps.executeUpdate();
            if (rcode > 0) {
                conn.commit();
            }
            else {
                System.out.println("Table not altered");
            }
            conn.setAutoCommit(true);
            return getPlaylist(playlist_id);  // return altered playlist

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in adding video to playlist: " + e.getMessage());
        }
    }

    public Playlist removeVideoFromPlaylist(int video_id, int playlist_id) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.REMOVE_VIDEO_FROM_PLAYLIST_GIVEN_IDS);
            ps.setInt(1, video_id);
            ps.setInt(2, playlist_id);
            conn.setAutoCommit(false);
            int rcode = ps.executeUpdate();
            if (rcode > 0) {
                conn.commit();
            }
            else {
                System.out.println("Table not altered");
            }
            return getPlaylist(playlist_id);  // return altered playlist

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in removing video from playlist: " + e.getMessage());
        }
    }

    private Playlist generatePlaylist(ResultSet rset) throws Exception {
        int id = rset.getInt("id");
        String name = rset.getString("name");
        Playlist pl = new Playlist(id,name);
        return pl;
    }

    Video generateVideo(ResultSet rset) throws Exception {
        int id = rset.getInt("videos.id");
        String name = rset.getString("videos.name");
        String url = rset.getString("url");
        String dialogue = rset.getString("dialogue");
        Date dateCreated = rset.getDate("date_created");
        int tlpId = rset.getInt("tlp_id");
        boolean isRemote = rset.getBoolean("is_remote");
        boolean isAvailable = rset.getBoolean("is_available");
        Video video = new Video(id,name, url, dialogue, dateCreated, tlpId, isRemote, isAvailable);
        List<String> characters = getCharacters(id);
        video.addCharacters(characters);
        return video;
    }

    List<String> getCharacters(int videoId) throws SQLException{
        try {
            String query = SqlStatementProvider.GET_CHARACTERS_GIVEN_VIDEO_ID;
            List<String> characters = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, videoId);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                String character = resultSet.getString("name");
                characters.add(character);
            }

            resultSet.close();
            ps.close();

            return characters;
        }catch(SQLException e) {
            e.printStackTrace();
            throw new SQLException(new StringBuilder().
                    append("Failed in getting constant: ").
                    append(e.getStackTrace()).toString());
        }
    }
}
