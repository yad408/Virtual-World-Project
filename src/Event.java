final class Event
{
    Action action;
    long time;
    Entity entity;

    public Event(Action action, long time, Entity entity) {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    public Entity getEntity(){return entity;}

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction(){
        return action;
    }

    public long getTime() {
        return time;
    }

}
