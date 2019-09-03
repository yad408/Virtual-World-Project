import processing.core.PImage;

import java.util.List;

abstract class Miner extends AnimateEntity {

    int resourceCount;
    int resourceLimit;

    Miner(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    Point nextPositionMiner(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - getPosition().x);
        Point newPos = new Point(getPosition().x + horiz, getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - getPosition().y);
            newPos = new Point(getPosition().x, getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = getPosition();
            }
        }

        return newPos;
    }
}
