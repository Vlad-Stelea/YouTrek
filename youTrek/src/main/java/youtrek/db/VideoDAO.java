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

    public Video getVideo(int id) throws SQLException {
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
            throw new SQLException("Failed in getting video: " + e.getMessage());
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
                    append("Failed in getting videos: ").
                    append(e.getStackTrace()).toString());
        }
    }

    public ListOfVideos getPublicSegments() throws SQLException {
        try {
            ListOfVideos videoSegments = new ListOfVideos();
            Video currentVideo = null;
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.GET_ALL_PUBLIC_VIDEOS);
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

    public void deleteVideoWithId(int id) throws SQLException {
        try {
            String query = SqlStatementProvider.DELETE_VIDEO_GIVEN_ID;

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(new StringBuilder().
                    append("Failed in deleting video with id: ").
                    append(id).
                    append(e.getStackTrace()).toString());
        }
    }

    /**
     * Inserts the passed in video object to the database
     * @param video the video to store in the database
     * @return The id of the newly passed in video object
     * @throws SQLException
     */
    public int createVideo(Video video) throws SQLException{
        try {
            String query = SqlStatementProvider.CREATE_VIDEO;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, video.name);
            ps.setBoolean(2, video.isRemote);
            ps.setBoolean(3, video.isAvailable);
            ps.setString(4, video.url);
            ps.setString(5, video.dialogue);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            rs.close();
            ps.close();

            return id;
        }catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(new StringBuilder().
                    append("Failed in creating video: ").
                    append(e.getStackTrace()).toString());
        }
    }

    public Video setVideoAvailability(int videoId, boolean isAvail) throws SQLException {
        try {
            String query = SqlStatementProvider.UPDATE_VIDEO_AVAILABILITY;

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setBoolean(1, isAvail);
            ps.setInt(2, videoId);
            ps.executeUpdate();
            ps.close();

            return getVideo(videoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed in changing video availability: " + e.getMessage());
        }
    }

    public void insertVideoCharactersPair(int videoId, List<Integer> characterIds) throws SQLException {
        try {
            String query = SqlStatementProvider.INSERT_VIDEO_CHARACTER_PAIR;
            Connection conn = DatabaseUtil.connect();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(query);
            for (Integer charId : characterIds) {
                ps.setInt(1, videoId);
                ps.setInt(2, charId);
                ps.addBatch();
            }

            ps.executeBatch();
            conn.commit();
            //Set autocommit back to normal
            conn.setAutoCommit(true);
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(new StringBuilder().
                    append("Failed in inserting into vcjoin: ").
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
