package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class Block {
    
    private Random rand;
    private final int X, Y, BLOCK_WIDTH;
    
    public Block() {
        rand = new Random();
        X = rand.nextInt(600);
        Y = rand.nextInt(600);
        BLOCK_WIDTH = 20;
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(new Color(89, 30, 20));
        g.fillRect(X, Y, BLOCK_WIDTH, BLOCK_WIDTH);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(X, Y, BLOCK_WIDTH, BLOCK_WIDTH);
    }
}
