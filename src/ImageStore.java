import processing.core.PApplet;
import processing.core.PImage;

import java.util.*;

final class ImageStore {
    private static final int KEYED_IMAGE_MIN = 5;
    private static final int COLOR_MASK = 0xffffff;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;

    private final Map<String, List<PImage>> images;
    private final List<PImage> defaultImages;

    public ImageStore(PImage defaultImage) {
        this.images = new HashMap<>();
        defaultImages = new LinkedList<>();
        defaultImages.add(defaultImage);
    }

    void loadImages(Scanner in,
                    PApplet screen) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                processImageLine(images, in.nextLine(), screen);
            } catch (NumberFormatException e) {
                System.out.println(String.format("Image format error on line %d",
                        lineNumber));
            }
            lineNumber++;
        }
    }

    List<PImage> getImageList(String key) {
        return images.getOrDefault(key, defaultImages);
    }

    private void processImageLine(Map<String, List<PImage>> images,
                                  String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = getImages(images, key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN) {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    setAlpha(img, screen.color(r, g, b));
                }
            }
        }
    }

    private List<PImage> getImages(Map<String, List<PImage>> images,
                                   String key) {
        return images.computeIfAbsent(key, k -> new LinkedList<>());
    }

    /*
     * Called with color for which alpha should be set and alpha value.
     * setAlpha(img, color(255, 255, 255), 0));
     */
    private void setAlpha(PImage img, int maskColor) {
        int alphaValue = 0;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }
}