import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class Vein extends AbstractEntity {

    private static final String ORE_ID_PREFIX = "ore -- ";

    Vein(String id, Point position, int actionPeriod, List<PImage> images) {
        super(id, position, actionPeriod, images);
    }

    public void executeActivity(EventScheduler scheduler, Activity activity) {
        WorldModel world = activity.world;
        ImageStore imageStore = activity.imageStore;

        Optional<Point> openPt = getPosition().findOpenAround(world);

        if (openPt.isPresent()) {
            Ore ore = new Ore(ORE_ID_PREFIX + getId(), openPt.get(), getActionPeriod(), imageStore.getImageList(WorldModel.ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActivity(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), getActionPeriod());

    }

}