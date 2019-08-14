import java.util.List;

import processing.core.PImage;

public final class Background
{
    List<PImage> images;
    int imageIndex;

    public Background(List<PImage> images) {
        this.images = images;
    }

    static PImage getCurrentImage(Object entity) {
        if (entity instanceof Background) {
            return ((Background)entity).images.get(
                    ((Background)entity).imageIndex);
        }
        else if (entity instanceof Entity) {
            return ((Entity)entity).getImages().get(((Entity)entity).getImageIndex());
        }
        else {
            throw new UnsupportedOperationException(
                    String.format("getCurrentImage not supported for %s",
                            entity));
        }
    }
}
