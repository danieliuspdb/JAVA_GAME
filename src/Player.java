import processing.core.PApplet;
import processing.core.PImage;

public class Player extends AnimatedSprite{
    //private static final int RIGHT_FACING = ;
    int lives = 3;
    boolean onPlatform, inPlace;
    PImage[] standLeft;
    PImage[] standRight;
    PImage[] jumpLeft;
    PImage[] jumpRight;
    public Player(PApplet parent, PImage img, float scale){
        super(parent, img, scale);
        lives = 3;
        direction = Game.RIGHT_FACING;
        onPlatform = true;
        inPlace = true;
        standLeft = new PImage[1];
        standLeft[0] = parent.loadImage("player_stand_left.png");
        standRight = new PImage[1];
        standRight[0] = parent.loadImage("player_stand_right.png");
        jumpLeft = new PImage[1];
        jumpLeft[0] = parent.loadImage("player_jump_left.png");
        jumpRight = new PImage[1];
        jumpRight[0] = parent.loadImage("player_jump_right.png");
        moveLeft = new PImage[2];
        moveLeft[0] = parent.loadImage("player_move_left1.png");
        moveLeft[1] = parent.loadImage("player_move_left2.png");
        moveRight = new PImage[2];
        moveRight[0] = parent.loadImage("player_move_right1.png");
        moveRight[1] = parent.loadImage("player_move_right2.png");
        currentImages = standRight;
    }

    @Override
    public void updateAnimation(){
        onPlatform = Game.isOnPlatforms(this, Game.platforms);
        inPlace = change_x == 0 && change_y == 0;
        super.updateAnimation();
    }

    @Override
    public void selectDirection(){
        if(change_x > 0)
            direction = Game.RIGHT_FACING;
        else if (change_x < 0)
            direction = Game.LEFT_FACING;
    }

    @Override
    public void selectCurrentImages(){
        if(direction == Game.RIGHT_FACING){
            if(inPlace)
                currentImages = standRight;
            else if(!onPlatform)
                currentImages = jumpRight;
            else
                currentImages = moveRight;
        }
        else if(direction == Game.LEFT_FACING){
            if(inPlace)
                currentImages = standLeft;
            else if(!onPlatform)
                currentImages = jumpLeft;
            else
                currentImages = moveLeft;
        }
    }
}
