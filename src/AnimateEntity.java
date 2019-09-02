import processing.core.PImage;

import java.util.List;

abstract public class AnimateEntity extends ActionEntity {

    private int animationPeriod;

    public AnimateEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public Action createAnimationAction(int repeatCount) {
        return new Animation(this, null, null, repeatCount);
    }

    public void nextImage() {
        setImageIndex((this.getImageIndex() + 1) % this.getImages().size());
    }


    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), getActionPeriod());
        scheduler.scheduleEvent(this, createAnimationAction(0), animationPeriod);
    }

}
