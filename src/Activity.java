
class Activity implements Action {

    public AbstractEntity entity;
    public WorldModel world;
    public ImageStore imageStore;

    Activity(AbstractEntity entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void execute(EventScheduler scheduler) {
        entity.executeActivity(scheduler, this);
    }
}
