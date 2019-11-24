package youtrek.models.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import youtrek.models.ListOfVideos;
import youtrek.models.Video;

import java.io.IOException;
import java.util.List;

public class ListOfVideosAdapter extends TypeAdapter<ListOfVideos> {
    private static final String videosKey = "videos";
    @Override
    public void write(JsonWriter jsonWriter, ListOfVideos listOfVideos) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name(videosKey);
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public ListOfVideos read(JsonReader jsonReader) throws IOException {
        //TODO Not need right now so will leave blank
        //HACK to not allow developer to use yet
        throw new IOException("The read method of ListOfVideosAdapter has not been implemented yet");
    }

    private static void populateArray(List<Video> test){

    }
}
