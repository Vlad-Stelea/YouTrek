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
                    generateUniqueBucketKeyForVideo() + ".ogg";
            S3Util.getInstance().uploadVideoToBucket(BUCKET_LOCATION, videoKey, uploadVideoPostRequest.getVideo());

            //Insert into the videos table
            Video insertVideo = new Video(uploadVideoPostRequest.getName(), "/" + videoKey, uploadVideoPostRequest.getDialogue());
            insertVideo.id = VideoDAO.getInstance().createVideo(insertVideo);

            //Parse the characters passed in as a string and turn them into character objects
            List<Character> allCharacters = convertNamesToCharacters(uploadVideoPostRequest.getCharacters());
            //Insert unknown characters into the characters table
            List<Integer> characterIds = charDao.insertCharacters(allCharacters);

            //Insert the video and character ids into the join table
            VideoDAO.getInstance().insertVideoCharactersPair(insertVideo.id, characterIds);

            Video insertedVideo = VideoDAO.getInstance().getVideo(insertVideo.id);
            return new UploadVideoResponse(insertedVideo, headers, 200);
        } catch (Exception e) {
            return new UploadVideoResponse(null, headers, 400);
        }
    }

    List<Character> convertNamesToCharacters(List<String> names) {
        return names.stream().map(Character::new).collect(Collectors.toList());
    }

    String generateUniqueBucketKeyForVideo() {
        return UUID.randomUUID().toString();
    }


}
