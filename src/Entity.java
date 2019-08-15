import processing.core.PImage;

import java.util.List;

public interface Entity
{
    void nextImage();
    int getAnimationPeriod();
    List<PImage> getImages();
    Point getPosition();
    void setPosition(Point pt);
    int getImageIndex();
}