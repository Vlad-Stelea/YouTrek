package youtrek.db;

import youtrek.models.ListOfVideos;
import youtrek.models.Video;

import java.sql.*;

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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM videos WHERE id=?;");
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM videos;");
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
            String query = "SELECT DISTINCT videos.id, videos.name, url, dialogue, date_created, tlp_id, is_remote, is_available\n" +
                    "FROM videos\n" +
                    "    INNER JOIN vcjoin on videos.id = vcjoin.video_id\n" +
                    "    INNER Join characters on vcjoin.character_id = characters.id\n" +
                    "WHERE dialogue LIKE '%%s%' OR videos.name LIKE '%%s%' OR characters.name LIKE '%%s%';".replaceAll("%s", filter);

            ListOfVideos videoSegments = new ListOfVideos();
            PreparedStatement ps = conn.prepareStatement(query);
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
