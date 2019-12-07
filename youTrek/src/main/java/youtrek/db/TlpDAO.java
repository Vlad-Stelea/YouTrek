package youtrek.db;

import youtrek.http.RegisterTlpResponse;
import youtrek.models.ListOfTlp;
import youtrek.models.RegisterTlpResponseBody;

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

    public ListOfTlp getAllTLP() throws SQLException{
        try {
            ListOfTlp lotlp = new ListOfTlp();
            RegisterTlpResponseBody currentTLP = null;
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.GET_ALL_TLP);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                currentTLP = generateTLP(resultSet);
                lotlp.appendTLP(currentTLP);
            }
            resultSet.close();
            ps.close();

            return lotlp;
    } catch (Exception e) {
        e.printStackTrace();
        throw new SQLException("Failed in getting list of tlp: " + e.getMessage());
    }
}
    RegisterTlpResponseBody generateTLP(ResultSet rset) throws Exception {
        int id = rset.getInt("id");
        String url = rset.getString("base_url");
        RegisterTlpResponseBody aTLP = new RegisterTlpResponseBody(id, url);
        return aTLP;
    }
}
