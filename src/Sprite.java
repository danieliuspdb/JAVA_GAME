import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {
    public PApplet parent;
    public PImage image;
    public float center_x, center_y;
    public float change_x, change_y;
    public float w, h;

    public Sprite(PApplet parent, String filename, float scale, float x, float y){
        this.parent = parent;
        image = parent.loadImage(filename);
        w = image.width * scale;
        h = image.height * scale;
        center_x = x;
        center_y = y;
        change_x = 0;
        change_y = 0;
    }

    public Sprite(PApplet parent,PImage img, float scale) {
        this.parent = parent;
        image = img;
        w = image.width * scale;
        h = image.height * scale;
        center_x = 0;
        center_y = 0;
        change_x = 0;
        change_y = 0;
    }
    public Sprite(PImage img, float scale){
        image = img;
        w = image.width * scale;
        h = image.height * scale;
        center_x = 0;
        center_y = 0;
        change_x = 0;
        change_y = 0;

    }
    public void display(){
        parent.image(image, center_x, center_y, w, h);
    }

    public void update(){
        center_x += change_x;
        center_y += change_y;
    }

    void setLeft(float left){
        center_x = left + w/2;
    }

    float getLeft(){
        return center_x - w/2;
    }

    void setRight(float right){
        center_x = right - w/2;
    }

    float getRight(){
        return center_x + w/2;
    }

    void setTop(float top){
        center_y = top + h/2;
    }

    float getTop(){
        return center_y - h/2;
    }

    void setBottom(float bottom){
        center_y = bottom - h/2;
    }

    float getBottom(){
        return center_y + h/2;
    }
}
