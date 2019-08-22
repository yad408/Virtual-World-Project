class Animation implements Action {

    public AnimateEntity entity;
    int repeatCount;

    Animation(AnimateEntity entity, int repeatCount) {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public void execute(EventScheduler scheduler) {
        entity.executeAnimation(scheduler, this);
    }
}