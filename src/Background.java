import processing.core.PImage;

import java.util.List;

public final class Background
{
    private final String id;
    private final List<PImage> images;
    private int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

    PImage getCurrentImage() {
        return images.get(imageIndex);
    }
}
