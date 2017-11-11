package snakegame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Sean
 */
public class Snake {
    
    private final int SNAKE_WIDTH;
    private double x, y;
    private double moveSpeed;
    
    public Snake(int x, int y) {
       this.x = x;
       this.y = y;
       moveSpeed = 1;
       SNAKE_WIDTH = 10;
    }
   
    public void paintComponent(Graphics g) {
        g.fillRect((int)x, (int)y, SNAKE_WIDTH, SNAKE_WIDTH);
    }
    
    public int getX() {
        return (int)x;
    }
    
    public int getY() {
        return (int)y;
    }
    
    public void setX(int newX) {
        x = newX;
    }
    
    public void setY(int newY) {
       y = newY;
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, SNAKE_WIDTH, SNAKE_WIDTH);
    }

}
