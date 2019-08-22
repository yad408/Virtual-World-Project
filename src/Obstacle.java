import processing.core.PImage;

import java.util.List;

final class Obstacle extends Entity {

    Obstacle(String id, Point position,
             List<PImage> images) {
        super(id, position, images);
    }

}
