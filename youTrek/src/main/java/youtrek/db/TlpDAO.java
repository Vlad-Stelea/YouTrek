package youtrek.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TlpDAO {
    Connection conn;

    private static TlpDAO instance;

    public static TlpDAO getInstance() {
        if(instance == null) instance = new TlpDAO();
        return instance;
    }

    private TlpDAO() {
        try  {
            conn = DatabaseUtil.connect();
        } catch (Exception e) {
            conn = null;
        }
    }

    public int insertUrl(String url) throws SQLException {
        try {
            String query = SqlStatementProvider.REGISTER_TLP;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,url);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int id = rs.getInt(1);

            rs.close();
            ps.close();

            return id;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(new StringBuilder().
                    append("Failed in registering site: ").
                    append(e.getStackTrace()).toString());
        }
    }

    public String getUrlById(int id) throws SQLException{
        try {
            //TODO fill with actual value
            String query = SqlStatementProvider.GET_TLP_BY_ID;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            String url = rs.getString("base_url");

            rs.close();
            ps.close();

            return url;
        }catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(new StringBuilder().
                    append("Failed in get the url from id: ").
                    append(e.getStackTrace()).toString());
        }
    }
}
