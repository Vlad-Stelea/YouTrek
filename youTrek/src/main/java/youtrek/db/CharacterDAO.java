package youtrek.db;

import youtrek.models.Character;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Inserts the characters specified into the database if they don't already exist in the database and returns the ids of all the characters in the list
     * @param characters List of characters to insert into the characters table
     * @return List of integers of the id's in the list of characters passed in
     * @throws SQLException
     */
    public List<Integer> insertCharacters(List<Character> characters) throws SQLException{
        //List of characters which are already stored in the database
        List<Character> charactersAlreadyInDB = getCharacters();
        KnownUnknownCharacters kUC = splitKnownAndUnknownCharacters(charactersAlreadyInDB, characters);
        try{
            Connection conn = DatabaseUtil.connect();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(SqlStatementProvider.CREATE_CHARACTER, Statement.RETURN_GENERATED_KEYS);

            //Set up the batch for adding unknown characters to the database
            for(Character character: kUC.unKnownCharacters) {
                ps.setString(1, character.name);
                ps.addBatch();
            }

            //Insert characters through a batch update for performance reasons
            ps.executeBatch();
            conn.commit();
            //Set autocommit back to normal
            conn.setAutoCommit(true);

            ResultSet rs = ps.getGeneratedKeys();
            List<Integer> ids = new ArrayList<>(kUC.knownCharacters.keySet());

            while(rs.next()) {
                int currentId = rs.getInt(1);
                ids.add(currentId);
            }

            rs.close();
            ps.close();

            return ids;
        } catch(Exception e) {
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

    KnownUnknownCharacters splitKnownAndUnknownCharacters(List<Character> charactersInDataBase, List<Character> allCharactersToCheck) {
        List<Character> knownCharacters = new ArrayList<>();
        List<Character> unkownCharacters = new ArrayList<>();
        Map<String, Character> allCharacters = new HashMap<>();

        //Populate Name to character map
        for(Character character : charactersInDataBase) {
            allCharacters.put(character.name, character);
        }

        for(Character character : allCharactersToCheck) {
            if(allCharacters.containsKey(character.name)) {
                character.id = allCharacters.get(character.name).id;
                knownCharacters.add(character);
            } else {
                unkownCharacters.add(character);
            }
        }

        return new KnownUnknownCharacters(knownCharacters, unkownCharacters);
    }

    //Data class to store which characters are known and unknown
    static class KnownUnknownCharacters {
        Map<Integer, Character> knownCharacters;
        List<Character> unKnownCharacters;

        public KnownUnknownCharacters(List<Character> knownCharacters, List<Character> unknownCharacters) {
            this.knownCharacters = new HashMap<>();
            populateKnownCharacters(knownCharacters);
            this.unKnownCharacters = unknownCharacters;
        }

        private void populateKnownCharacters(List<Character> knownCharacters) {
            for(Character character : knownCharacters) {
                this.knownCharacters.put(character.id, character);
            }
        }
    }
}
