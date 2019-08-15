import processing.core.PImage;

import java.util.List;

public class Blacksmith implements Entity {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    private Blacksmith(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }


    public void nextImage() {
        imageIndex = (this.getImageIndex() + 1) % this.getImages().size();
    }

    public List<PImage> getImages() {
        return images;
    }

    public Point getPosition(){
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    static Blacksmith createBlacksmith(
            String id, Point position, List<PImage> images)
    {
        return new Blacksmith(id, position, images, 0, 0, 0,
                0);
    }







}
