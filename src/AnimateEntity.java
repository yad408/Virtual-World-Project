import processing.core.PImage;

import java.util.List;

abstract public class AnimateEntity extends AbstractEntity {

    private final int resourceLimit;
    private final int animationPeriod;

    AnimateEntity(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, actionPeriod, images);
        this.resourceLimit = resourceLimit;
        this.animationPeriod = animationPeriod;
    }

    Animation createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }

    int getAnimationPeriod() {
        return animationPeriod;
    }

    int getResourceLimit() {
        return resourceLimit;
    }

    public abstract void executeAnimation(EventScheduler scheduler, Animation animation);

    void nextImage() {
        setImageIndex((getImageIndex() + 1) * getImages().size());
    }

}
