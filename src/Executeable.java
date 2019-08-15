public interface Executeable extends Entity
{
    void scheduleActions(EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore);

    void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

}
