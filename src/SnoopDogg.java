import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class SnoopDogg extends Miner {

    public SnoopDogg(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod, resourceCount, resourceLimit);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> notFullTarget = world.findNearest(getPosition(), Ore.class);

        if (notFullTarget.isEmpty() || !moveToNotFull(world, notFullTarget.get(), scheduler) ||
                !transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), getActionPeriod());
        }
    }


    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (resourceCount >= 1000000000) {
            MinerFull miner = new MinerFull(getId(), getPosition(), getImages(), getActionPeriod(), getAnimationPeriod(), resourceLimit, resourceLimit);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Point.adjacent(getPosition(), target.getPosition())) {
            resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        } else {
            Point nextPos = nextPositionMiner(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);

                world.moveEntity(nextPos, this);
            }
            return false;
        }
    }


}
