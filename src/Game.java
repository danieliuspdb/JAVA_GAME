import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Game extends PApplet{
    final static float MOVE_SPEED = 5;
    final static float SPRITE_SCALE = 50.0f/128;
    final static float SPRITE_SIZE = 50;
    final static float GRAVITY = 0.06F;
    final static float JUMP_SPEED = 5;
    final static float RIGHT_MARGIN = 400;
    final static float LEFT_MARGIN = 60;
    final static float VERTICAL_MARGIN = 40;
    final static int NEUTRAL_FACING = 0;
    final static int RIGHT_FACING = 1;
    final static int LEFT_FACING = 2;
    final static float WIDTH = SPRITE_SIZE * 16;
    final static float HEIGHT = SPRITE_SIZE * 12;
    final static float GROUND_LEVEL = HEIGHT - SPRITE_SIZE;
    Player player;
    float view_x;
    float view_y;
    int x = 0;
    int level_1 = 0, level_2 = 0, level_3 = 0;

    int numCoins;
    boolean isGameOver;

    PImage snow, crate, brown_brick, red_brick, gold, spider, p;
    static ArrayList<Sprite> platforms;
    ArrayList<Sprite> coins;
    ArrayList<Sprite> enemy;
    public void settings(){
        size(800, 600);
    }

    public void setup() {
        imageMode(CENTER);
        p = loadImage("sprite1.png");
        player = new Player(this, p, 0.8f);
        player.setBottom(GROUND_LEVEL);
        player.center_x = 100;
        enemy = new ArrayList<Sprite>();
        platforms = new ArrayList<Sprite>();
        coins = new ArrayList<Sprite>();
        spider = loadImage("spider_walk_right1.png");
        gold = loadImage("gold1.png");
        snow = loadImage("snow.png");
        crate = loadImage("crate.png");
        brown_brick = loadImage("brown_brick.png");
        red_brick = loadImage("red_brick.png");
        if(x == 0)
            createPlatforms("maps1.csv");
        else if(x == 1)
            createPlatforms("maps2.csv");
        else if(x == 2)
            createPlatforms("maps3.csv");
        view_x = 0;
        view_y = 0;
        isGameOver = false;
        numCoins = 0;
    }

    public void draw(){

        scroll();

        background(255);
        for(Sprite s: platforms)
            s.display();

        for(Sprite c: coins) {
            c.display();
        }
        player.display();

        if(enemy.size() == 0)
            System.out.println("no enemy");

        for(Sprite e: enemy)
        {
            System.out.println("enemy");
            e.display();
        }


        fill(255, 0, 0);
        textSize(32);
        text("Coin: " + numCoins, view_x + 50, view_y + 50);
        text("Lives: " + player.lives, view_x + 50, view_y + 100);

        if(isGameOver){
            fill(0, 0, 255);
            text("Game Over!", view_x + width/2 - 100, view_y + height/2);
            if(player.lives == 0)
                text("You Lose!", view_x + width/2 -100, view_y + height/2 + 50);
            else
                text("You Win!", view_x + width/2 -100, view_y + height/2 + 50);
            text("Press X To Restart!",view_x + width/2 -100, view_y + height/2 + 100);
            if(x == 0 && player.lives > 0) {
                text("Press 1 To Go To 2nd Level!", view_x + width / 2 - 100, view_y + height / 2 + 150);
                level_1 = 1;
            }
            else if(x == 1 && player.lives > 0) {
                text("Press 2 To Go To 3rd Level!", view_x + width / 2 - 100, view_y + height / 2 + 150);
                level_2 = 1;
            }
            else if(x == 2 && player.lives > 0) {
                text("Press 3 To Go To 1st Level!", view_x + width / 2 - 100, view_y + height / 2 + 150);
                level_3 = 1;
            }
        }

        if(!isGameOver) {
            player.updateAnimation();
            resolvePlatformCollisions(player, platforms);
            for(Sprite e: enemy) {
                e.update();
                ((AnimatedSprite)e).updateAnimation();
            }
            for(Sprite c: coins) {
                ((AnimatedSprite)c).updateAnimation();
            }
            collectCoins();
            checkDeath();
        }
    }

    void collectCoins(){
        ArrayList<Sprite> coin_list = checkCollisionList(player, coins);
        if(coin_list.size() > 0)
            for(Sprite coin: coin_list)
            {
                numCoins++;
                coins.remove(coin);
            }
        if(coins.size() == 0)
            isGameOver = true;
    }

    void checkDeath(){
        boolean collideEnemy = (checkCollisionList(player, enemy).size() > 0);
        boolean fallOfCliff = player.getTop() > GROUND_LEVEL;
        if(collideEnemy || fallOfCliff) {
            player.lives--;

            if (player.lives == 0)
                isGameOver = true;
            else {
                player.center_x = 100;
                player.setBottom(GROUND_LEVEL);
            }
        }
    }

    void scroll(){
        float right_boundary = view_x + width - RIGHT_MARGIN;
        if(player.getRight() > right_boundary)
            view_x += player.getRight() - right_boundary;

        float left_boundary = view_x + LEFT_MARGIN;
        if(player.getLeft() < left_boundary)
            view_x -= left_boundary - player.getLeft();

       /* float bottom_boundary = view_y + height - VERTICAL_MARGIN;
        if(player.getBottom() > bottom_boundary)
            view_y += player.getBottom() - bottom_boundary;*/

        float top_boundary = view_y + VERTICAL_MARGIN;
        if(player.getTop() < top_boundary)
            view_y -= top_boundary - player.getTop();

        translate(-view_x, -view_y);
    }

    public static boolean isOnPlatforms(Sprite s, ArrayList<Sprite> walls){
        s.center_y += 5;
        ArrayList<Sprite> col_list = checkCollisionList(s, walls);
        s.center_y -= 5;
        if(col_list.size() > 0){
            return true;
        }
        else
            return false;
    }

    public void resolvePlatformCollisions(Sprite s, ArrayList<Sprite> walls){
        s.change_y += GRAVITY;

        s.center_y += s.change_y;
        ArrayList<Sprite> col_list = checkCollisionList(s, walls);
        if(col_list.size() > 0){
            Sprite collided = col_list.get(0);
            if(s.change_y > 0){
                s.setBottom(collided.getTop());
            }
            else if(s.change_y < 0){
                s.setTop(collided.getBottom());
            }
            s.change_y = 0;
        }

        s.center_x += s.change_x;
        col_list = checkCollisionList(s, walls);
        if(col_list.size() > 0){
            Sprite collided = col_list.get(0);
            if(s.change_x > 0){
                s.setRight(collided.getLeft());
            }
            else if(s.change_x < 0){
                s.setLeft(collided.getRight());
            }
        }
    }

    static boolean checkCollision(Sprite s1, Sprite s2){
        boolean noXOverlap = s1.getRight() <= s2.getLeft() || s1.getLeft() >= s2.getRight();
        boolean noYOverlap = s1.getBottom() <= s2.getTop() || s1.getTop() >= s2.getBottom();

        if(noXOverlap || noYOverlap)
            return false;
        else
            return true;
    }

    public static ArrayList<Sprite> checkCollisionList(Sprite s, ArrayList<Sprite> list){
        ArrayList<Sprite> collision_list = new ArrayList<Sprite>();
        for(Sprite p: list){
            if(checkCollision(s, p))
                collision_list.add(p);
        }
        return collision_list;
    }

    void createPlatforms(String filename){
        String[] lines = loadStrings(filename);
        for(int row = 0; row< lines.length; row++){
            String[] values = split(lines[row], ";");
            for(int col = 0; col < values.length; col++){
                if(values[col].equals("1")){
                    Sprite s = new Sprite(this, red_brick, SPRITE_SCALE);
                    s.center_x = SPRITE_SIZE/2 + col * SPRITE_SIZE;
                    s.center_y = SPRITE_SIZE/2 + row * SPRITE_SIZE;
                    platforms.add(s);
                }
                else if(values[col].equals("2")){
                    Sprite s = new Sprite(this, snow, SPRITE_SCALE);
                    s.center_x = SPRITE_SIZE/2 + col * SPRITE_SIZE;
                    s.center_y = SPRITE_SIZE/2 + row * SPRITE_SIZE;
                    platforms.add(s);
                }
                else if(values[col].equals("3")){
                    Sprite s = new Sprite(this, brown_brick, SPRITE_SCALE);
                    s.center_x = SPRITE_SIZE/2 + col * SPRITE_SIZE;
                    s.center_y = SPRITE_SIZE/2 + row * SPRITE_SIZE;
                    platforms.add(s);
                }
                else if(values[col].equals("4")){
                    Sprite s = new Sprite(this, crate, SPRITE_SCALE);
                    s.center_x = SPRITE_SIZE/2 + col * SPRITE_SIZE;
                    s.center_y = SPRITE_SIZE/2 + row * SPRITE_SIZE;
                    platforms.add(s);
                }
                else if(values[col].equals("5")){
                    Coin c = new Coin(this, gold, SPRITE_SCALE);
                    c.center_x = SPRITE_SIZE/2 + col * SPRITE_SIZE;
                    c.center_y = SPRITE_SIZE/2 + row * SPRITE_SIZE;
                    coins.add(c);
                }
                else if(values[col].equals("6")){
                    float bLeft = col* SPRITE_SIZE;
                    float bRight = bLeft + 4 * SPRITE_SIZE;
                    Enemy e = new Enemy(this, spider, 50/72.0f, bLeft, bRight);
                    e.center_x = SPRITE_SIZE/2 + col * SPRITE_SIZE;
                    e.center_y = SPRITE_SIZE/2 + row * SPRITE_SIZE;
                    enemy.add(e);
                }
            }
        }
    }
    public static void main(String[] args) {
        PApplet.main("Game");
    }

    public void keyPressed(){
        if(keyCode == RIGHT)
            player.change_x = MOVE_SPEED;
        else if(keyCode == LEFT)
            player.change_x = -MOVE_SPEED;
        else if(key == ' ' && isOnPlatforms(player, platforms)){
            player.change_y = -JUMP_SPEED;
        }
        else if((key == 'x' || key == 'X') && isGameOver){
            setup();
        }
        else if((key == '1') && isGameOver && level_1 == 1){
            x = 1;
            setup();
        }
        else if((key == '2') && isGameOver && level_2 == 1){
            x = 2;
            setup();
        }
        else if((key == '3') && isGameOver && level_3 == 1){
            x = 0;
            setup();
        }
    }

    public void keyReleased(){
        if(keyCode == RIGHT)
            player.change_x = 0;
        else if(keyCode == LEFT)
            player.change_x = 0;
    }
}
