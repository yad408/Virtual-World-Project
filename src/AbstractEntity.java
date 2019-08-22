import processing.core.PImage;

import java.util.List;

abstract public class AbstractEntity extends Entity {

    private final int actionPeriod;
    private int imageIndex;

    AbstractEntity(String id, Point position, int actionPeriod, List<PImage> images) {
        super(id, position, images);
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
    }

    int getActionPeriod() {
        return actionPeriod;
    }

    int getImageIndex() {
        return imageIndex;
    }

    void setImageIndex(int val) {
        imageIndex = val;
    }

    Activity createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    void scheduleActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), actionPeriod);
    }

    public abstract void executeActivity(EventScheduler scheduler, Activity activity);

}
