import java.util.Random;

class Cloud {

    void smoke(Point location, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        smokecloud(location, world, imageStore);

        SnoopDogg snoop = Functions.createSnoop(location, imageStore.getImageList("snoop"));
        world.addEntity(snoop);
        snoop.scheduleActions(scheduler, world, imageStore);
    }

    private void smokecloud(Point center, WorldModel world, ImageStore imageStore) {
        int x = center.getX(), y = center.getY();
        for (int i = x - 5; i <= x + 5; i++) {
            for (int j = y - 5; j <= y + 5; j++) {
                Point cell = new Point(i, j);

                if (world.withinBounds(cell)) {
                    Background background = world.getBackgroundCell(cell);

                    if (background.getId().equals("grass")) {
                        Random random = new Random();

                        if (random.nextInt(50) <= 30 || cell.equals(center)) {
                            Background smoke = new Background("cloud", imageStore.getImageList("cloud"));
                            world.setBackground(cell, smoke);
                        }
                    }
                }

            }
        }
    }
}
