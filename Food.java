package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Sean
 */
public class Food {
    
    private int x, y;
    private final int DIAMETER;
    
    public Food(int x, int y) {
        this.x = x;
        this.y = y;
        DIAMETER = 10;
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(new Color(236, 148, 64));
        g.fillOval(x, y, DIAMETER, DIAMETER);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }
}
