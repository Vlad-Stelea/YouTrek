package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.constants.Constants;
import youtrek.db.VideoDAO;
import youtrek.http.DeleteVideoRequest;
import youtrek.http.DeleteVideoResponse;
import youtrek.models.ListOfVideos;
import youtrek.models.Video;
import youtrek.s3.S3Util;

import java.util.HashMap;
import java.util.Map;

public class DeleteVideoHandler implements RequestHandler<DeleteVideoRequest, DeleteVideoResponse> {
    @Override
    public DeleteVideoResponse handleRequest(DeleteVideoRequest deleteVideoRequest, Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        try {
            int videoId = deleteVideoRequest.getId();
            Video videoToDelete = VideoDAO.getInstance().getVideo(videoId);

            //Use the s3DeleteKey to remove the video from the
            String s3DeleteKey = getVideoKey(videoToDelete.url);
            S3Util.getInstance().deleteFile(Constants.S3_VIDEO_BUCKET_LOCATION, s3DeleteKey);

            //Delete the video with the given id from the table
            VideoDAO.getInstance().deleteVideoWithId(videoId);

            //Return all the videos left in the library
            ListOfVideos allVideos = VideoDAO.getInstance().getVideoSegments();
            return new DeleteVideoResponse(allVideos, headers, 200);
        }catch (Exception e) {
            ListOfVideos lov = new ListOfVideos();
            return new DeleteVideoResponse(lov, headers, 400);
        }

    }

    String getVideoKey(String videoLocation) {
        return videoLocation.substring(1);
    }
}
