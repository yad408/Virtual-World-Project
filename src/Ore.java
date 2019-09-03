import processing.core.PImage;

import java.util.List;
import java.util.Random;

final class Ore extends ActionEntity {

    private static final Random rand = new Random();
    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MAX = 150;
    private static final int BLOB_ANIMATION_MIN = 50;

    public Ore(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = getPosition();

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = new OreBlob(getId() + BLOB_ID_SUFFIX, pos, imageStore.getImageList(BLOB_KEY), getActionPeriod() / BLOB_PERIOD_SCALE, BLOB_ANIMATION_MIN + rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }
}