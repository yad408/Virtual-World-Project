import processing.core.PImage;

import java.util.List;

public interface Entity {

    String getId();

    List<PImage> getImages();

    Point getPosition();

    void setPosition(Point newPosition);

    PImage getCurrentImage();
}