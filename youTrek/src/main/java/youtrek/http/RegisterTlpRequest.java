package youtrek.http;

public class RegisterTlpRequest extends AbstractYouTrekRequest{
    String url;

    public RegisterTlpRequest(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
