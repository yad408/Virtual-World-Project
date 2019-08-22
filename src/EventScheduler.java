import java.util.*;

final class EventScheduler {
    private PriorityQueue<Event> eventQueue;
    private Map<Entity, List<Event>> pendingEvents;
    private final double timeScale;

    public EventScheduler(double timeScale) {
        this.eventQueue = new PriorityQueue<>(new EventComparator());
        this.pendingEvents = new HashMap<>();
        this.timeScale = timeScale;
    }

    void unscheduleAllEvents(Entity entity) {
        List<Event> pending = pendingEvents.remove(entity);

        if (pending != null) {
            for (Event event : pending) {
                eventQueue.remove(event);
            }
        }
    }

    private void removePendingEvent(Event event) {
        List<Event> pending = pendingEvents.get(event.entity);

        if (pending != null) {
            pending.remove(event);
        }
    }

    void updateOnTime(long time) {
        while (!eventQueue.isEmpty() &&
                eventQueue.peek().time < time) {
            Event next = eventQueue.poll();

            this.removePendingEvent(next);

            next.action.execute(this);
        }
    }

    void scheduleEvent(Entity entity, Action action, long afterPeriod) {
        long time = System.currentTimeMillis() +
                (long) (afterPeriod * timeScale);
        Event event = new Event(action, time, entity);

        eventQueue.add(event);

        // update list of pending events for the given entity
        List<Event> pending = pendingEvents.getOrDefault(entity,
                new LinkedList<>());
        pending.add(event);
        pendingEvents.put(entity, pending);
    }
}
