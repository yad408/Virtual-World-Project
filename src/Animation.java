public class Animation implements Action {

    private AnimateEntity entity;
    private WorldModel world;
    private ImageStore imageStore;
    int repeatCount;

    Animation(AnimateEntity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
        entity.nextImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(entity, entity.createAnimationAction(Math.max(repeatCount - 1, 0)), entity.getAnimationPeriod());
        }
    }
}