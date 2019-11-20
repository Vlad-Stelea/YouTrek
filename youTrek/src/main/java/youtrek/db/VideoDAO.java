package youtrek.db;

import youtrek.models.Video;
import youtrek.db.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;


public class VideoDAO {
    java.sql.Connection conn;

    public VideoDAO() {
        try  {
            conn = DatabaseUtil.connect();
        } catch (Exception e) {
            conn = null;
        }
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
        }

        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed in getting constant: " + e.getMessage());
        }
    }

    public ArrayList<Video> getVideoSegments() throws Exception {
        try {
            ArrayList<Video> videoSegments = new ArrayList<Video>();
            Video currentVideo = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM videos;");
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                currentVideo = generateVideo(resultSet);
                videoSegments.add(currentVideo);
            }
            resultSet.close();
            ps.close();

            return videoSegments;
        }

        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed in getting constant: " + e.getMessage());
        }
    }


    public Video generateVideo(ResultSet rset) throws Exception {
        int id = rset.getInt("id");
        String name = rset.getString("name");
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
