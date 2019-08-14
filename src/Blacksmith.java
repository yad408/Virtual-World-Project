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

    public Blacksmith(
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

    public Point getPosition(){
        return position;
    }

    public void setPosition(Point pt) {
        this.position = pt;
    }

    public List<PImage> getImages() {
        return images;
    }

    public void setImages(List<PImage> images){
        this.images = images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int idx) {
        this.imageIndex = idx;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public void setResourceLimit(int lim) {
        this.resourceLimit = lim;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int cnt){
        this.resourceCount = cnt;
    }

    public int getActionPeriod() {
        return  actionPeriod;
    }

    public void setActionPeriod(int act){
        this.actionPeriod = act;
    }

    public int getAnimationPeriod(){
        return animationPeriod;
    }

    public void setAnimationPeriod(int an) {
        this.animationPeriod = an;
    }

    void nextImage() {
        this.imageIndex = (this.getImageIndex() + 1) % this.getImages().size();
    }


    public static Blacksmith createBlacksmith(
            String id, Point position, List<PImage> images)
    {
        return new Blacksmith(id, position, images, 0, 0, 0,
                0);
    }
}
