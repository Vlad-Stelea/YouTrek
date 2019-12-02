package youtrek.db;

import youtrek.models.Character;
import youtrek.models.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class VCJoinDAO {
    private static VCJoinDAO instance;

    private VCJoinDAO() {

    }

    public static VCJoinDAO getInstance() {
        if(instance == null) instance = new VCJoinDAO();
        return instance;
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
}
