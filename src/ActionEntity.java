import processing.core.PImage;

import java.util.List;

abstract public class ActionEntity implements Entity {

    private String id;
    private Point position;
    private int actionPeriod;
    private int imageIndex;
    private List<PImage> images;

    ActionEntity(String id, Point position, List<PImage> images, int actionPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
    }


    public List<PImage> getImages() {
        return images;
    }

    public PImage getCurrentImage() {
        return images.get(imageIndex);
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point pt) {
        position = pt;
    }

    public int getImageIndex() {
        return imageIndex;
    }
    void setImageIndex(int val) {
        imageIndex = val;
    }

    public int getActionPeriod() {
        return actionPeriod;
    }


    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);


    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
    }
}
