public final class Event
{
    Action action;
    long time;
    Entity entity;

    public Event(Action action, long time, Entity entity) {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }
}
