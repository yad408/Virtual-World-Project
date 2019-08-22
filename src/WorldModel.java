import processing.core.PImage;

import java.util.*;

final class WorldModel {
    static final String ORE_KEY = "ore";
    private static final int VEIN_ACTION_PERIOD = 4;
    private static final int VEIN_ROW = 3;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ID = 1;
    private static final int VEIN_NUM_PROPERTIES = 5;
    private static final String VEIN_KEY = "vein";
    private static final int SMITH_ROW = 3;
    private static final int SMITH_COL = 2;
    private static final int SMITH_ID = 1;
    private static final int SMITH_NUM_PROPERTIES = 4;
    private static final String SMITH_KEY = "blacksmith";
    private static final int ORE_ACTION_PERIOD = 4;
    private static final int ORE_ROW = 3;
    private static final int ORE_COL = 2;
    private static final int ORE_ID = 1;
    private static final int ORE_NUM_PROPERTIES = 5;
    private static final int OBSTACLE_ROW = 3;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final String OBSTACLE_KEY = "obstacle";
    private static final int MINER_ANIMATION_PERIOD = 6;
    private static final int MINER_ACTION_PERIOD = 5;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_ROW = 3;
    private static final int MINER_COL = 2;
    private static final int MINER_ID = 1;
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final String MINER_KEY = "miner";
    private static final int BGND_ROW = 3;
    private static final int BGND_COL = 2;
    private static final int BGND_ID = 1;
    private static final int BGND_NUM_PROPERTIES = 4;
    private static final String BGND_KEY = "background";
    private static final int PROPERTY_KEY = 0;
    private final Background[][] background;
    private final Entity[][] occupancy;
    int numRows;
    int numCols;
    Set<Entity> entities;
    Set<AbstractEntity> AbstractEntities;
    Set<AnimateEntity> animateEntities;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();
        this.AbstractEntities = new HashSet<>();
        this.animateEntities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }


    private void setBackgroundCell(Point pos,
                                   Background background) {
        this.background[pos.y][pos.x] = background;
    }

    private Background getBackgroundCell(Point pos) {
        return background[pos.y][pos.x];
    }

    private void setOccupancyCell(Point pos,
                                  Entity entity) {
        occupancy[pos.y][pos.x] = entity;
    }

    Entity getOccupancyCell(Point pos) {
        return occupancy[pos.y][pos.x];
    }

    Optional<Entity> getOccupant(Point pos) {
        if (pos.isOccupied(this)) {
            return Optional.of(this.getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }

    private void tryAddEntity(Entity entity) {
        if (entity.getPosition().isOccupied(this)) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        this.addEntity(entity);
    }


    private void setBackground(Point pos,
                               Background background) {
        if (pos.withinBounds(this)) {
            this.setBackgroundCell(pos, background);
        }
    }

    Optional<PImage> getBackgroundImage(Point pos) {
        if (pos.withinBounds(this)) {
            return Optional.of(this.getBackgroundCell(pos).getCurrentImage());
        } else {
            return Optional.empty();
        }
    }

    private void removeEntityAt(Point pos) {
        if (pos.withinBounds(this)
                && this.getOccupancyCell(pos) != null) {
            Entity entity = this.getOccupancyCell(pos);
            entity.setPosition(new Point(-1, -1));
            entities.remove(entity);
            this.setOccupancyCell(pos, null);
        }
    }

    void removeEntity(Entity entity) {
        this.removeEntityAt(entity.getPosition());
    }

    void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (pos.withinBounds(this) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            this.removeEntityAt(pos);
            this.setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    void addEntity(Entity entity) {
        if (entity.getPosition().withinBounds(this)) {
            this.setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
        }
        if (entity instanceof AbstractEntity) {
            AbstractEntity actE = (AbstractEntity) entity;
            AbstractEntities.add(actE);
        }
        if (entity instanceof AnimateEntity) {
            AnimateEntity aniE = (AnimateEntity) entity;
            animateEntities.add(aniE);
        }
    }


    private boolean parseVein(String[] properties,
                              ImageStore imageStore) {
        if (properties.length == VEIN_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                    Integer.parseInt(properties[VEIN_ROW]));
            Vein vein = new Vein(properties[VEIN_ID],
                    pt,
                    Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                    imageStore.getImageList(VEIN_KEY));
            this.tryAddEntity(vein);
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }

    private boolean parseSmith(String[] properties,
                               ImageStore imageStore) {
        if (properties.length == SMITH_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            Blacksmith blacksmith = new Blacksmith(properties[SMITH_ID],
                    pt, imageStore.getImageList(SMITH_KEY));
            this.tryAddEntity(blacksmith);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    private boolean parseOre(String[] properties,
                             ImageStore imageStore) {
        if (properties.length == ORE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                    Integer.parseInt(properties[ORE_ROW]));
            Ore ore = new Ore(properties[ORE_ID],
                    pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                    imageStore.getImageList(ORE_KEY));
            this.tryAddEntity(ore);
        }

        return properties.length == ORE_NUM_PROPERTIES;
    }

    private boolean parseObstacle(String[] properties,
                                  ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(
                    Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Obstacle obstacle = new Obstacle(properties[OBSTACLE_ID],
                    pt, imageStore.getImageList(OBSTACLE_KEY));
            this.tryAddEntity(obstacle);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    private boolean parseMiner(String[] properties,
                               ImageStore imageStore) {
        if (properties.length == MINER_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                    Integer.parseInt(properties[MINER_ROW]));
            MinerNotFull minerNF = new MinerNotFull(properties[MINER_ID],
                    Integer.parseInt(properties[MINER_LIMIT]),
                    pt,
                    Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                    Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                    imageStore.getImageList(MINER_KEY));
            this.tryAddEntity(minerNF);
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }

    private boolean parseBackground(String[] properties,
                                    ImageStore imageStore) {
        if (properties.length == BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            this.setBackground(pt,
                    new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

    private boolean processLine(String line,
                                ImageStore imageStore) {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[PROPERTY_KEY]) {
                case BGND_KEY:
                    return this.parseBackground(properties, imageStore);
                case MINER_KEY:
                    return this.parseMiner(properties, imageStore);
                case OBSTACLE_KEY:
                    return this.parseObstacle(properties, imageStore);
                case ORE_KEY:
                    return this.parseOre(properties, imageStore);
                case SMITH_KEY:
                    return this.parseSmith(properties, imageStore);
                case VEIN_KEY:
                    return this.parseVein(properties, imageStore);
            }
        }

        return false;
    }

    void load(Scanner in, ImageStore imageStore) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!this.processLine(in.nextLine(), imageStore)) {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            } catch (NumberFormatException e) {
                System.err.println(String.format("invalid entry on line %d",
                        lineNumber));
            } catch (IllegalArgumentException e) {
                System.err.println(String.format("issue on line %d: %s",
                        lineNumber, e.getMessage()));
            }
            lineNumber++;
        }
    }
}