package youtrek.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListOfPublicSegments implements Iterable<PublicSegment> {
    List<PublicSegment> segments;

    public ListOfPublicSegments() {
        segments = new ArrayList<>();
    }

    // Convert list of videos to list of public segments
    public ListOfPublicSegments(ListOfVideos lov) {
        segments = new ArrayList<>();
        for (Video v : lov) {
            this.appendSegment(new PublicSegment(v));
        }
    }

    @Override
    public Iterator<PublicSegment> iterator() {
        return segments.iterator();
    }

    public void appendSegment(PublicSegment segment) {
        segments.add(segment);
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