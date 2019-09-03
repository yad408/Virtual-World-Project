import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class MinerFull extends Miner {

    public MinerFull(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod, resourceCount, resourceLimit);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), Blacksmith.class);
        if (fullTarget.isPresent() && moveToFull(world, fullTarget.get(), scheduler)) {
            transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }


    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Point.adjacent(getPosition(), target.getPosition())) {
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

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        MinerNotFull miner = new MinerNotFull(getId(), getPosition(), getImages(), getActionPeriod(), getAnimationPeriod(), 0, resourceLimit);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }


}
