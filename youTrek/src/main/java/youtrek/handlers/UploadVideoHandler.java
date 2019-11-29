package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.VideoDAO;
import youtrek.http.UploadVideoPostRequest;
import youtrek.http.UploadVideoResponse;
import youtrek.models.Video;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UploadVideoHandler implements RequestHandler<UploadVideoPostRequest, UploadVideoResponse> {
    @Override
    public UploadVideoResponse handleRequest(UploadVideoPostRequest uploadVideoPostRequest, Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try {
            Video insertVideo = new Video(uploadVideoPostRequest.getName(), "testurl.og", uploadVideoPostRequest.getDialogue());
            int insertedVideoId = VideoDAO.getInstance().createVideo(insertVideo);

        } catch(SQLException e) {
            //TODO handle case where video is not fully uploaded
        }
        return null;
    }
}
