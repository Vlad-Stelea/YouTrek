package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.CharacterDAO;
import youtrek.db.VCJoinDAO;
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
    private final String BUCKET_LOCATION = "xscratch-videos";
    @Override
    public UploadVideoResponse handleRequest(UploadVideoPostRequest uploadVideoPostRequest, Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        CharacterDAO charDao = CharacterDAO.getInstance();
        try {
            //Upload video into s3 bucket
            String videoKey =
                    generateUniqueBucketKeyForVideo() +
                    ".ogg";
            S3Util.getInstance().uploadVideoToBucket(BUCKET_LOCATION, videoKey, uploadVideoPostRequest.getVideo());

            //Insert into the videos table
            Video insertVideo = new Video(uploadVideoPostRequest.getName(), "/" + videoKey, uploadVideoPostRequest.getDialogue());
            insertVideo.id = VideoDAO.getInstance().createVideo(insertVideo);

            //Get which Characters are already in the characters table
            List<Character> videoCharacters = convertNamesToCharacters(uploadVideoPostRequest.getCharacters());
            List<Character> allCharactersInTable = charDao.getCharacters();
            KnownUnknownCharacters kUC = splitKnownAndUnknownCharacters(allCharactersInTable, videoCharacters);

            //Insert unknown characters into the characters table
            List<Integer> uploadedCharacterIds = charDao.insertCharacters(kUC.unKnownCharacters);
            List<Integer> alreadyKnownCharIds = kUC.knownCharacters.stream().map(character -> character.id).collect(Collectors.toList());
            //Combine the two character id lists
            alreadyKnownCharIds.addAll(uploadedCharacterIds);

            //Insert the video and character ids into the join table
            VCJoinDAO.getInstance().insertVideoCharactersPair(insertVideo.id, alreadyKnownCharIds);

        } catch(SQLException e) {
            //TODO handle case where video data is not fully uploaded
            //TODO Remove all inserted items incase of error
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

    String generateUniqueBucketKeyForVideo() {
        return UUID.randomUUID().toString();
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
