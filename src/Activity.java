public class Activity implements Action {

    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;

    private Activity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler) {
        if (this.entity instanceof MinerFull){
            ((MinerFull)this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if (this.entity instanceof MinerNotFull){
            ((MinerNotFull)this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if (entity instanceof Ore){
            ((Ore)entity).execute(world, imageStore, scheduler);
        }
        if (entity instanceof OreBlob){
            ((OreBlob)entity).execute(world, imageStore, scheduler);
        }
        if (entity instanceof Quake){
            ((Quake)entity).execute(world, imageStore, scheduler);
        }
        if (entity instanceof Vein){
            ((Vein)entity).execute(world, imageStore, scheduler);
        }
    }

    static Activity createActivityAction(Entity entity, WorldModel world, ImageStore imageStore){
        return new Activity(entity, world, imageStore);
    }
}
