package youtrek.db;

public class SqlStatementProvider {
    public final static String GET_VIDEOS_GIVEN_ID = "SELECT * FROM videos WHERE id=?;";
    public final static String GET_ALL_VIDEOS = "SELECT * FROM videos;";
    public final static String GET_ALL_VIDEOS_GIVEN_FILTER =
            "SELECT DISTINCT videos.id, videos.name, url, dialogue, date_created, tlp_id, is_remote, is_available\n" +
            "FROM videos\n" +
            "    INNER JOIN vcjoin on videos.id = vcjoin.video_id\n" +
            "    INNER Join characters on vcjoin.character_id = characters.id\n" +
            "WHERE dialogue LIKE ? OR videos.name LIKE ? OR characters.name LIKE ?;";
    public final static String GET_CHARACTERS_GIVEN_VIDEO_ID =
            "SELECT *\n" +
            "FROM characters\n" +
            "LEFT JOIN vcjoin v on characters.id = v.character_id\n" +
            "where v.video_id = ?;";
}