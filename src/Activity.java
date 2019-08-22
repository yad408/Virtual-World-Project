public class Activity extends Action {

    public Activity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore, int repeatCount)
    {
        super(entity, world, imageStore, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        if (this.entity instanceof MinerFull){
            ((MinerFull)this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if (this.entity instanceof MinerNotFull){
            ((MinerNotFull)this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if (this.entity instanceof Ore){
            ((Ore)this.entity).execute(world, imageStore, scheduler);
        }
        if (this.entity instanceof OreBlob){
            ((OreBlob)this.entity).execute(world, imageStore, scheduler);
        }
        if (this.entity instanceof Quake){
            ((Quake)this.entity).execute(world, imageStore, scheduler);
        }
        if (this.entity instanceof Vein){
            ((Vein)this.entity).execute(world, imageStore, scheduler);
        }
    }

    public static Activity createActivityAction(Entity entity, WorldModel world, ImageStore imageStore){
        return new Activity(entity, world, imageStore, 0);
    }
}
