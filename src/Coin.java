import processing.core.PApplet;
import processing.core.PImage;


public class Coin extends AnimatedSprite{

    public Coin(PApplet parent, PImage img, float scale){
        super(parent, img, scale);
        standNeutral = new PImage[4];
        standNeutral[0] = parent.loadImage("gold1.png");
        standNeutral[1] = parent.loadImage("gold2.png");
        standNeutral[2] = parent.loadImage("gold3.png");
        standNeutral[3] = parent.loadImage("gold4.png");
        currentImages = standNeutral;
    }
}
