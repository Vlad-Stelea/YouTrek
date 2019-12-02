package youtrek.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import youtrek.db.VideoDAO;
import youtrek.http.DeleteVideoRequest;
import youtrek.http.DeleteVideoResponse;
import youtrek.models.ListOfVideos;
import youtrek.models.Video;

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
            String s3DeleteKey = getVideoKey(videoToDelete.url);
            //TODO use the s3DeleteKey to remove the video from the

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
        return videoLocation;
    }
}
