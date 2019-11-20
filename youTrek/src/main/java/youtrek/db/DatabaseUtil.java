package youtrek.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
    public final static String rdsMySqlDatabaseUrl = "chekovdb.cgfw4tm7ipq1.us-east-2.rds.amazonaws.com";
    public final static String dbUsername = "admin";
    public final static String dbPassword = "password"; // TODO extra feature -- use AWS secrets manager instead

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

            // Heineman example reformatted
            String jdbcUrl2 =  jdbcTag + rdsMySqlDatabaseUrl + ":" + rdsMySqlDatabasePort + "/" + dbSchema + multiQueries;
            conn = DriverManager.getConnection(jdbcUrl2, dbUsername, dbPassword);

            return conn;
        } catch (Exception ex) {
            throw new Exception("Failed in database connection");
        }
    }
}
