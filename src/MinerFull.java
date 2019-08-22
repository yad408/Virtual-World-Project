import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class MinerFull extends AnimateEntity {

    MinerFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, resourceLimit, position, actionPeriod, animationPeriod, images);
    }

    public void executeAnimation(EventScheduler scheduler, Animation animation) {
        nextImage();

        if (animation.repeatCount != 1) {
            scheduler.scheduleEvent(this, createAnimationAction(Math.max(animation.repeatCount - 1, 0)), getAnimationPeriod());
        }
    }

    public void executeActivity(EventScheduler scheduler, Activity activity) {
        WorldModel world = activity.world;
        ImageStore imageStore = activity.imageStore;

        Optional<Entity> fullTarget = getPosition().findNearest(world, "Blacksmith");

        if (fullTarget.isPresent() &&
                this.moveToFull(world, fullTarget.get(), scheduler)) {
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    getActionPeriod());
        }
    }

    private Point nextPositionMiner(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - getPosition().x);
        Point newPos = new Point(getPosition().x + horiz,
                getPosition().y);

        if (horiz == 0 || newPos.isOccupied(world)) {
            int vert = Integer.signum(destPos.y - getPosition().y);
            newPos = new Point(getPosition().x,
                    getPosition().y + vert);

            if (vert == 0 || newPos.isOccupied(world)) {
                newPos = getPosition();
            }
        }

        return newPos;
    }

    private boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacent(target.getPosition())) {
            return true;
        } else {
            Point nextPos = nextPositionMiner(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    private void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        MinerNotFull miner = new MinerNotFull(getId(), getResourceLimit(),
                getPosition(), getActionPeriod(), getAnimationPeriod(),
                getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActivity(scheduler, world, imageStore);
    }
}
