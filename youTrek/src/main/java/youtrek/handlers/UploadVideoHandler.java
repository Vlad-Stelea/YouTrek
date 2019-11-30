package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.CharacterDAO;
import youtrek.db.VideoDAO;
import youtrek.http.UploadVideoPostRequest;
import youtrek.http.UploadVideoResponse;
import youtrek.models.Character;
import youtrek.models.Video;
import youtrek.s3.S3Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class UploadVideoHandler implements RequestHandler<UploadVideoPostRequest, UploadVideoResponse> {
    @Override
    public UploadVideoResponse handleRequest(UploadVideoPostRequest uploadVideoPostRequest, Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        CharacterDAO charDao = CharacterDAO.getInstance();
        try {
            //Upload video into s3 bucket
            String location = new StringBuilder().append("/")
                    .append(uploadVideoPostRequest.getName())
                    .append(".ogg").toString();
            S3Util.getInstance().uploadVideoToBucket(location, uploadVideoPostRequest.getName(), uploadVideoPostRequest.getVideo());

            //Insert into the videos table
            Video insertVideo = new Video(uploadVideoPostRequest.getName(), "testurl.og", uploadVideoPostRequest.getDialogue());
            int insertedVideoId = VideoDAO.getInstance().createVideo(insertVideo);

            //Get which Characters are already in the characters table
            List<Character> videoCharacters = convertNamesToCharacters(uploadVideoPostRequest.getCharacters());
            List<Character> allCharactersInTable = charDao.getCharacters();
            KnownUnknownCharacters kUC = splitKnownAndUnknownCharacters(allCharactersInTable, videoCharacters);

            //Insert unknown characters into the characters table
            List<Integer> uploadedCharacterIds = charDao.insertCharacters(kUC.unKnownCharacters);

            //TODO Insert into vcjoin


        } catch(SQLException e) {
            //TODO handle case where video data is not fully uploaded
        } catch(IOException e) {
            //TODO handle error with uploading video to s3
        }
        return null;
    }

    List<Character> convertNamesToCharacters(List<String> names) {
        return names.stream().map(Character::new).collect(Collectors.toList());
    }

    KnownUnknownCharacters splitKnownAndUnknownCharacters(List<Character> dataBaseCharacters, List<Character> requestCharacters) {
        List<Character> knownCharacters = new ArrayList<>();
        List<Character> unkownCharacters = new ArrayList<>();
        Map<String, Character> allCharacters = new HashMap<>();

        //Populate Name to character map
        for(Character character : dataBaseCharacters) {
            allCharacters.put(character.name, character);
        }

        for(Character character : requestCharacters) {
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
        List<Character> knownCharacters;
        List<Character> unKnownCharacters;

        public KnownUnknownCharacters(List<Character> knownCharacters, List<Character> unknownCharacters) {
            this.knownCharacters = knownCharacters;
            this.unKnownCharacters = unknownCharacters;
        }
    }
}
