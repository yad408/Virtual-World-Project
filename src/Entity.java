import processing.core.PImage;

import java.util.List;

abstract public class Entity
{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int actionPeriod;
    private int animationPeriod;

    public Entity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }


    public String getId(){
        return id;
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public List<PImage> getImages(){
        return images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public Point getPosition(){
        return position;
    }

    public void setPosition(Point pt){
        this.position = pt;
    }

    public int getActionPeriod(){
        return actionPeriod;

    }

    public void nextImage(){
        imageIndex = (this.getImageIndex() + 1) % this.getImages().size();
    }


}