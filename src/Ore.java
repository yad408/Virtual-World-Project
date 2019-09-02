import processing.core.PImage;

import java.util.List;
import java.util.Random;

final class Ore extends ActionEntity {

    public static final Random rand = new Random();
    private static final int ORE_NUM_PROPERTIES = 5;
    private static final int ORE_ID = 1;
    private static final int ORE_COL = 2;
    private static final int ORE_ROW = 3;
    private static final int ORE_ACTION_PERIOD = 4;
    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MAX = 150;
    private static final int BLOB_ANIMATION_MIN = 50;

    public Ore(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }


    //executeActivity
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = getPosition();    // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = new OreBlob(getId() + BLOB_ID_SUFFIX, pos, imageStore.getImageList(BLOB_KEY), getActionPeriod() / BLOB_PERIOD_SCALE, BLOB_ANIMATION_MIN + rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }
}