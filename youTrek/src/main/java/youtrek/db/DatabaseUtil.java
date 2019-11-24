package youtrek.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUtil {
    // TODO note that these fields can be completely removed if we want to commit to secrets manager
//    public final static String rdsMySqlDatabaseUrl = "chekovdb.cgfw4tm7ipq1.us-east-2.rds.amazonaws.com";
//    public final static String dbUsername = "admin";
//    public final static String dbPassword = "password";
  
    public final static String jdbcTag = "jdbc:mysql://";
    public final static String rdsMySqlDatabasePort = "3306";
    public final static String multiQueries = "?allowMultiQueries=true";

    public final static String dbSchema = "v2"; // TODO update this every time we switch schema!

    // pooled across all usages.
    static Connection conn;

    /**
     * Singleton access to DB connection to share resources effectively across multiple accesses.
     */
    protected static Connection connect() throws Exception {
        if (conn != null) return conn;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            KeyManager km = KeyManager.getInstance();

            // Use with secrets manager
            String jdbcUrl1 =  jdbcTag + km.getHost() + ":" + rdsMySqlDatabasePort + "/" + dbSchema + multiQueries;
            conn = DriverManager.getConnection(jdbcUrl1, km.getUser(), km.getPass());

            // Old method with plaintext passwords
            //String jdbcUrl2 =  jdbcTag + rdsMySqlDatabaseUrl + ":" + rdsMySqlDatabasePort + "/" + dbSchema + multiQueries;
            //conn = DriverManager.getConnection(jdbcUrl2, dbUsername, dbPassword);

            return conn;
        } catch (Exception ex) {
            throw new Exception("Failed in database connection");
        }
    }

    protected static ResultSet runQuery(String query) throws Exception {
        PreparedStatement ps = connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        ps.close();
        return rs;
    }
}
