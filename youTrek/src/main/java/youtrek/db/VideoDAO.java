package youtrek.db;

import youtrek.models.ListOfVideos;
import youtrek.models.Video;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoDAO {
    java.sql.Connection conn;
    private static VideoDAO instance = null;
    private VideoDAO() {
        try  {
            conn = DatabaseUtil.connect();
        } catch (Exception e) {
            
            conn = null;
        }
    }

    public static VideoDAO getInstance() {
        if(instance == null) instance = new VideoDAO();
        return instance;
    }

    //TODO decide on where/when to add the add/remove video functions
    public Video getVideo(int id) throws Exception {
        try {
            Video video = null;
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.GET_VIDEOS_GIVEN_ID);
            ps.setInt(1,  id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                video = generateVideo(resultSet);
            }
            resultSet.close();
            ps.close();

            return video;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed in getting video: " + e.getMessage());
        }
    }

    public ListOfVideos getVideoSegments() throws SQLException {
        try {
            ListOfVideos videoSegments = new ListOfVideos();
            Video currentVideo = null;
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.GET_ALL_VIDEOS);
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
            throw new SQLException("Failed in getting list of videos: " + e.getMessage());
        }
    }

    public ListOfVideos getVideoSegments(String filter) throws SQLException {
        try {
            String query = SqlStatementProvider.GET_ALL_VIDEOS_GIVEN_FILTER;
            String modifiedFilter = new StringBuffer().append("%").append(filter).append("%").toString();
            ListOfVideos videoSegments = new ListOfVideos();

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, modifiedFilter);
            ps.setString(2, modifiedFilter);
            ps.setString(3, modifiedFilter);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Video video = generateVideo(rs);
                videoSegments.appendVideo(video);
            }
            rs.close();
            ps.close();
            return videoSegments;
        }catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(new StringBuilder().
                    append("Failed in getting constant: ").
                    append(e.getStackTrace()).toString());
        }
    }

    public void deleteVideoWithId(int id) throws SQLException{
        try {
            String query = SqlStatementProvider.DELETE_VIDEO_GIVEN_ID;

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(new StringBuilder().
                    append("Failed in deleting video with id: ").
                    append(id).
                    append(e.getStackTrace()).toString());
        }
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
