final class Event {
    Action action;
    long time;
    public Entity entity;

    Event(Action action, long time, Entity entity) {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }
}