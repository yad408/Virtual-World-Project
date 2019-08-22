import processing.core.PImage;

import java.util.List;

public abstract class MinerEntity extends MoveableEntity
{
    protected int resourceLimit;
    protected int resourceCount;


    public MinerEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {

        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;

    }
    public int getResourceLimit(){return resourceLimit;}
    public int getResourceCount(){return resourceCount;}


    abstract public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler);



}

