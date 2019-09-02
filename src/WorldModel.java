import processing.core.PImage;

import java.util.*;

final class WorldModel {
    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 4;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;
    public static final String ORE_KEY = "ore";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    private static final int VEIN_ACTION_PERIOD = 4;
    public static final int ORE_ACTION_PERIOD = 4;
    public static final String SMITH_KEY = "blacksmith";
    public static final int SMITH_NUM_PROPERTIES = 4;
    public static final int SMITH_ID = 1;
    public static final int SMITH_COL = 2;
    public static final int SMITH_ROW = 3;
    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;
    public static final int KEYED_IMAGE_MIN = 5;
    public static final int PROPERTY_KEY = 0;
    public static final String BGND_KEY = "background";
    private static final String VEIN_KEY = "vein";
    private static final int VEIN_NUM_PROPERTIES = 5;
    private static final int VEIN_ID = 1;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ROW = 3;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;
    private static final String MINER_KEY = "miner";
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final int MINER_ID = 1;
    private static final int MINER_COL = 2;
    private static final int MINER_ROW = 3;
    private static final int MINER_ANIMATION_PERIOD = 6;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_ACTION_PERIOD = 5;
    public int numRows;
    public int numCols;
    public Background[][] background;
    public Entity[][] occupancy;
    public Set<Entity> entities;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }
    //+processImageLine()


    //load()
    public void load(Scanner in, ImageStore imageStore) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!processLine(in.nextLine(), imageStore)) {
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

    //+processLine(): boolean
    public boolean processLine(String line, ImageStore imageStore) {

        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[PROPERTY_KEY]) {
                case BGND_KEY:
                    return parseBackground(properties, imageStore);
                case MINER_KEY:
                    return parseMiner(properties, imageStore);
                case OBSTACLE_KEY:
                    return parseObstacle(properties, imageStore);
                case ORE_KEY:
                    return parseOre(properties, imageStore);
                case SMITH_KEY:
                    return parseSmith(properties, imageStore);
                case VEIN_KEY:
                    return parseVein(properties, imageStore);
            }
        }

        return false;
    }
    //+parseBackground(): boolean

    public boolean parseBackground(String[] properties, ImageStore imageStore) {
        if (properties.length == BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }
    //+parseMiner(): boolean

    public boolean parseMiner(String[] properties, ImageStore imageStore) {
        if (properties.length == MINER_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                    Integer.parseInt(properties[MINER_ROW]));
            Entity entity = new MinerNotFull(properties[MINER_ID],
                    pt, imageStore.getImageList(MINER_KEY),
                    Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                    Integer.parseInt(properties[MINER_ANIMATION_PERIOD]), 0,
                    Integer.parseInt(properties[MINER_LIMIT]));
            tryAddEntity(entity);
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }

    //+parseObstacle(): boolean
    public boolean parseObstacle(String[] properties, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]), Integer.parseInt(properties[OBSTACLE_ROW]));
            Entity entity = new Obstacle(properties[OBSTACLE_ID],
                    pt, imageStore.getImageList(OBSTACLE_KEY));
            tryAddEntity(entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    //+parseOre(): boolean
    public boolean parseOre(String[] properties, ImageStore imageStore) {
        if (properties.length == ORE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                    Integer.parseInt(properties[ORE_ROW]));
            Entity entity = new Ore(properties[ORE_ID],
                    pt, imageStore.getImageList(ORE_KEY), Integer.parseInt(properties[ORE_ACTION_PERIOD]));
            tryAddEntity(entity);
        }

        return properties.length == ORE_NUM_PROPERTIES;
    }

    //+parseSmith(): boolean

    public boolean parseSmith(String[] properties, ImageStore imageStore) {
        if (properties.length == SMITH_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            Entity entity = new Blacksmith(properties[SMITH_ID],
                    pt, imageStore.getImageList(SMITH_KEY));
            tryAddEntity(entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    //+parseVein(): boolean
    public boolean parseVein(String[] properties, ImageStore imageStore) {
        if (properties.length == VEIN_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                    Integer.parseInt(properties[VEIN_ROW]));
            Entity entity = new Vein(properties[VEIN_ID], pt,
                    imageStore.getImageList(VEIN_KEY), Integer.parseInt(properties[VEIN_ACTION_PERIOD]));
            tryAddEntity(entity);
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }

    //+withinBounds(): boolean
    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < numRows &&
                pos.x >= 0 && pos.x < numCols;
    }

    //+isOccupied(): boolean
    public boolean isOccupied(Point pos) {
        return withinBounds(pos) && getOccupancyCell(pos) != null;
    }

    //+nearestEntity(): Optional<Entity>
    public Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = Point.distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities) {
                int otherDistance = Point.distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    //+findNearest(): Optional<Entity>
    public Optional<Entity> findNearest(Point pos, Class entity1) {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : entities) {
            if (entity1.isInstance(entity)) {
                ofType.add(entity);
            }
        }

        return nearestEntity(ofType, pos);
    }

    //+addEntity()
    /*
        Assumes that there is no entity currently occupying the
        intended destination cell.
    */
    public void addEntity(Entity entity) {
        if (withinBounds(entity.getPosition())) {
            setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
        }
    }

    //tryAddEntity()
    public void tryAddEntity(Entity entity) {
        if (isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }

    // moveEntity()
    public void moveEntity(Point pos, Entity entity) {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && !pos.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            removeEntityAt(pos);
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    //+removeEntity()
    public void removeEntity(Entity entity) {
        removeEntityAt(entity.getPosition());
    }
    //+removeEntityAt()

    public void removeEntityAt(Point pos) {
        if (withinBounds(pos)
                && getOccupancyCell(pos) != null) {
            Entity entity = getOccupancyCell(pos);

            /* this moves the entity just outside of the grid for
                debugging purposes */
            entity.setPosition(new Point(-1, -1));
            entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }

    //+getBackgroundImage(): Optional<PImage>
    public Optional<PImage> getBackgroundImage(Point pos) {
        if (withinBounds(pos)) {
            return Optional.of(getBackgroundCell(pos).getCurrentImage());
        } else {
            return Optional.empty();
        }
    }

    //+setBackground()
    public void setBackground(Point pos, Background background) {
        if (withinBounds(pos)) {
            setBackgroundCell(pos, background);
        }
    }

    //+getOccupant(): Optional<Entity>
    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }

    //+getOccupancyCell(): Entity
    public Entity getOccupancyCell(Point pos) {
        return occupancy[pos.y][pos.x];
    }


    //+setOccupancyCell()
    public void setOccupancyCell(Point pos, Entity entity) {
        occupancy[pos.y][pos.x] = entity;
    }

    //+getBackgroundCell(): Background
    public Background getBackgroundCell(Point pos) {
        return background[pos.y][pos.x];
    }

    //+setBackgroundCell()
    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.y][pos.x] = background;
    }
}