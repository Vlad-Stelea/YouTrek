package youtrek.db;

import youtrek.models.Character;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterDAO {
    private static CharacterDAO instance;

    private CharacterDAO() {
        //Do nothing
    }

    public static CharacterDAO getInstance() {
        if(instance == null) instance = new CharacterDAO();
        return instance;
    }

    public List<Character> getCharacters() throws SQLException{
        try {
            Connection conn = DatabaseUtil.connect();
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.GET_ALL_CHARACTERS);

            List<Character> characters = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Character newChar = generateCharacter(rs);
                characters.add(newChar);
            }

            rs.close();
            ps.close();

            return characters;
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(new StringBuilder().
                    append("Failed in getting all characters: ").
                    append(e.getStackTrace()).toString());
        }
    }

    /**Inserts the names of characters specified into the characters table and returns their new id's
     *  Assumes that all the characters specified are not already in the table. If they exist in the table, they will be re-inserted
     * @param names List of names to insert into the characters table
     * @return List of integers of what the newly inserted id's of the characters are
     * @throws SQLException
     */
    public List<Integer> insertCharacters(List<Character> names) throws SQLException {
        try {
            Connection conn = DatabaseUtil.connect();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.CREATE_CHARACTER, Statement.RETURN_GENERATED_KEYS);

            for(Character character : names) {
                ps.setString(1, character.name);
                ps.addBatch();
            }

            //Insert characters through a batch update for performance reasons
            ps.executeBatch();
            conn.commit();
            //Set autocommit back to normal
            conn.setAutoCommit(true);

            //Populate the id's to return
            ResultSet rs = ps.getGeneratedKeys();
            List<Integer> ids = new ArrayList<>(names.size());
            while(rs.next()) {
                int currentId = rs.getInt(1);
                ids.add(currentId);
            }

            rs.close();
            ps.close();

            return ids;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(new StringBuilder().
                    append("Failed in creating characters: ").
                    append(e.getStackTrace()).toString());
        }
    }

    Character generateCharacter(ResultSet rset) throws SQLException{
        int id = rset.getInt("id");
        String name = rset.getString("name");
        return new Character(id, name);
    }
}
