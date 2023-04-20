import processing.core.PApplet;
import processing.core.PImage;
import java.io.PrintWriter;

public class TileMapEditor extends PApplet {

    // Constants for grid size and block types
    static final int GRID_SIZE = 16;
    static final int NUM_BLOCK_TYPES = 6;

    // 2D array to store the tile map
    int[][] tileMap;
    // Array to store the images for each block type
    PImage[] blockImages;
    // Current selected block type
    int selectedBlockType = 1;

    public static void main(String[] args) {
        PApplet.main("TileMapEditor");
    }

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        tileMap = new int[width / GRID_SIZE][height / GRID_SIZE];
        blockImages = new PImage[NUM_BLOCK_TYPES];
        //for (int i = 0; i < NUM_BLOCK_TYPES; i++) {
        blockImages[0] = loadImage("snow.png"); // Load image for each block type
        blockImages[1] = loadImage("brown_brick.png"); // Load image for each block type
        blockImages[2] = loadImage("crate.png"); // Load image for each block type
        blockImages[3] = loadImage("red_brick.png"); // Load image for each block type
        blockImages[4] = loadImage("gold1.png"); // Load image for each block type
        blockImages[5] = loadImage("spider_walk_left1.png"); // Load image for each block type
        //}
    }

    public void draw() {
        background(255);
        drawGrid();
        drawTileMap();
    }

    void drawGrid() {
        stroke(200);
        for (int x = 0; x < width; x += GRID_SIZE) {
            line(x, 0, x, height);
        }
        for (int y = 0; y < height; y += GRID_SIZE) {
            line(0, y, width, y);
        }
    }

    void drawTileMap() {
        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap[0].length; y++) {
                int tileType = tileMap[x][y];
                if (tileType > 0) {
                    image(blockImages[tileType - 1], x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE); // Display image for corresponding block type
                }
            }
        }
    }

    public void mousePressed() {
        int gridX = mouseX / GRID_SIZE;
        int gridY = mouseY / GRID_SIZE;
        if (gridX >= 0 && gridX < tileMap.length && gridY >= 0 && gridY < tileMap[0].length) {
            tileMap[gridX][gridY] = selectedBlockType;
        }
    }

    public void keyPressed() {
        if (key >= '1' && key <= '6') {
            selectedBlockType = key - '0';
        } else if (key == 's' || key == 'S') {
            saveTileMap();
        }
    }

    void saveTileMap() {
        PrintWriter writer = createWriter("tilemap.csv");
        for (int y = 0; y < tileMap[0].length; y++) {
            for (int x = 0; x < tileMap.length; x++) {
                writer.print(tileMap[x][y]);
                if (x < tileMap.length - 1) {
                    writer.print(";");
                }
            }
            writer.println();
        }
        writer.flush();
        writer.close();
    }
}