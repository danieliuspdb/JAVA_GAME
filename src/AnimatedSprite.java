import processing.core.PApplet;
import processing.core.PImage;

public class AnimatedSprite extends Sprite{
    PImage[] currentImages;
    PImage[] standNeutral;
    PImage[] moveRight;
    PImage[] moveLeft;
    int direction;
    int index;
    int frame;

    public AnimatedSprite(PApplet parent, PImage img, float scale){
        super(parent, img, scale);
        direction = Game.NEUTRAL_FACING;
        index = 0;
        frame = 0;
    }

    public void updateAnimation(){
        frame++;
        if(frame % 5 ==  0)
        {
            selectDirection();
            selectCurrentImages();
            advanceToNextImage();
        }
    }

    public void selectDirection(){
        if(change_x > 0)
            direction = Game.RIGHT_FACING;
        else if (change_x < 0)
            direction = Game.LEFT_FACING;
        else
            direction = Game.NEUTRAL_FACING;
    }

    public void selectCurrentImages(){
        if(direction == Game.RIGHT_FACING)
            currentImages = moveRight;
        else if (direction == Game.LEFT_FACING)
            currentImages = moveLeft;
        else
            currentImages = standNeutral;
    }

    public void advanceToNextImage(){
        index++;
        if(index >= currentImages.length){
            System.out.println("index: " + index);
            index = 0;
        }
        image = currentImages[index];
    }
}
