package youtrek.models;

public class PublicSegment {
    public final String url;
    public final String character;
    public final String text;

    public PublicSegment(String url, String character, String text) {
        this.url = url;
        this.character = character;
        this.text = text;
    }

    // convert video to public segment
    public PublicSegment(Video v) {
        this.url = v.url;
        this.character = String.join(",", v.characters); // need characters as string not list
        this.text = v.dialogue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (o instanceof PublicSegment) {
            PublicSegment ps = (PublicSegment) o;
            return  ps.url.equals(this.url)&&
                    ps.character.equals(this.character) &&
                    ps.text.equals(this.text);
        }
        return false;
    }
}

/*
{ "segments" :
  [
    { "url"        : "s3-bucket-url-1",
      "character"  : "char-1",
      "text"       : "text-1"
    },
    { "url"        : "s3-bucket-url-2",
      "character"  : "char-2",
      "text"       : "text-2"
    }
  ]
}
 */