import processing.core.PImage;

import java.util.List;

abstract public class AbstractEntity extends Entity {



    public AbstractEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }




    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world,
                                ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getAnimationPeriod());
        scheduler.scheduleEvent(this,
                Animation.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }



}
