package youtrek.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUtil {

    private final static String jdbcTag = "jdbc:mysql://";
    private final static String rdsMySqlDatabasePort = "3306";
    private final static String multiQueries = "?allowMultiQueries=true";

    private static String dbSchema = "v2"; // TODO update this every time we switch schema!

    /**
     * Useful for setting schema to test DB for junit
     */
    public static void setSchema(String schema) {
        dbSchema = schema;
    }

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

}
