package youtrek.http;

public class DeleteVideoRequest extends AbstractYouTrekRequest{
    int id;

    public DeleteVideoRequest() {
        //Empty constructor
    }

    public DeleteVideoRequest(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

}
