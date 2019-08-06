import java.util.List;

import processing.core.PImage;

public final class Background
{
    private String id;
    List<PImage> images;
    int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }
}
