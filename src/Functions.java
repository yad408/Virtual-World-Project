import processing.core.PImage;

import java.util.List;

public class Functions {

    public static SnoopDogg createSnoop(Point position, List<PImage> images) {
        return new SnoopDogg("snoop", position, images, 4500, 0, 0, 0);
    }

    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

}
