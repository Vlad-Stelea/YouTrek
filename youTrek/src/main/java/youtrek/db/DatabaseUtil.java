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

    public final static String dbName = "chekovdb";    // default created from MySQL WorkBench

    // pooled across all usages.
    static Connection conn;

    /**
     * Singleton access to DB connection to share resources effectively across multiple accesses.
     */
    protected static Connection connect() throws Exception {
        if (conn != null) { return conn; }

        try {
            //System.out.println("start connecting......");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    jdbcTag + rdsMySqlDatabaseUrl + ":" + rdsMySqlDatabasePort + "/" + dbName + multiQueries,
                    dbUsername,
                    dbPassword);
            //System.out.println("Database has been connected successfully.");
            return conn;
        } catch (Exception ex) {
            throw new Exception("Failed in database connection");
        }
    }
}
