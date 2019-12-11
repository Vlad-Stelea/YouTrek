package youtrek.handlers;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import youtrek.db.DatabaseUtil;
import youtrek.http.DeleteVideoRequest;
import youtrek.http.DeleteVideoResponse;
import youtrek.http.UploadVideoPostRequest;
import youtrek.http.UploadVideoResponse;
import youtrek.models.ListOfVideos;
import youtrek.models.Video;

import java.util.Arrays;

public class TestDeleteVideoHandler {

    UploadVideoHandler upHandler;
    DeleteVideoHandler deleteHandler;
    @Before
    public void setup() {
        DatabaseUtil.setSchema("testing");
        upHandler = new UploadVideoHandler();
        deleteHandler = new DeleteVideoHandler();
    }

    @Test
    public void testDeleteVideoHandler() throws Exception{
        //Upload video
        UploadVideoPostRequest upRequest = new UploadVideoPostRequest("name", "dialogue", Arrays.asList("Spock"), "rrrr");
        UploadVideoResponse response = upHandler.handleRequest(upRequest, null);
        Video vid = new Gson().fromJson(response.getBody(), Video.class);

        int id = vid.id;

        DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest(id);
        DeleteVideoResponse delResponse = deleteHandler.handleRequest(deleteVideoRequest, null);

        //Make sure that video is no longer in the database
        ListOfVideos videos = new Gson().fromJson(delResponse.getBody(), ListOfVideos.class);

        //Make sure that the added video no longer exists in the database
        for(Video video : videos) {
            if(video.id == id) Assert.fail("Video is still in the database");
        }

    }
}
