import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

public final class WorldView
{
    private PApplet screen;
    private WorldModel world;
    private int tileWidth;
    private int tileHeight;
    private Viewport viewport;

    WorldView(
            int numRows,
            int numCols,
            PApplet screen,
            WorldModel world,
            int tileWidth,
            int tileHeight)
    {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    private PApplet getScreen() {
        return screen;
    }

    private WorldModel getWorld() {
        return world;
    }

    private int getTileWidth() {
        return tileWidth;
    }

    private int getTileHeight() {
        return tileHeight;
    }

    private Viewport getViewport() {
        return viewport;
    }

    private int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

    void shiftView(int colDelta, int rowDelta) {
        int newCol = clamp(this.viewport.getCol() + colDelta, 0,
                this.world.getNumCols() - this.viewport.getNumCols());
        int newRow = clamp(this.viewport.getRow() + rowDelta, 0,
                this.world.getNumRows() - this.viewport.getNumRows());

        viewport.shift(newCol, newRow);
    }

    private void drawBackground() {
        for (int row = 0; row < this.viewport.getNumRows(); row++) {
            for (int col = 0; col < this.viewport.getNumCols(); col++) {
                Point worldPoint = this.viewport.viewportToWorld(col, row);
                Optional<PImage> image =
                        world.getBackgroundImage(worldPoint);
                if (image.isPresent()) {
                    this.screen.image(image.get(), col * this.tileWidth,
                            row * this.tileHeight);
                }
            }
        }
    }

    private void drawEntities() {
        for (Entity entity : this.getWorld().getEntities()) {
            Point pos = entity.getPosition();

            if (this.viewport.contains(pos)) {
                Point viewPoint = this.viewport.worldToViewport(pos.x, pos.y);
                this.screen.image(Background.getCurrentImage(entity),
                        viewPoint.x * this.tileWidth,
                        viewPoint.y * this.tileHeight);
            }
        }
    }

    void drawViewport() {
        drawBackground();
        drawEntities();
    }
}
