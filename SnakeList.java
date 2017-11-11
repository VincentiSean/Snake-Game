package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 *
 * @author Sean
 */
public class SnakeList {
    
    private ArrayList<Snake> snakeList;
    private final int STARTX, STARTY;
    private SnakeGame game;
    private double deltaX, deltaY, moveSpeed;
    private String direction;
    
    public SnakeList(SnakeGame game) {
        this.game = game;
        snakeList = new ArrayList<>();
        STARTX = game.getWidth()/2;
        STARTY = game.getHeight()/2;
        createSnake();
        deltaX = 0;
        deltaY = 0;
        moveSpeed = 1;
        direction = "nothing";
    }
    
    public void createSnake() {
        Snake first = new Snake(STARTX, STARTY);
        snakeList.add(first);
    }
    
    public void addSnake(int loopCount) {
        for (int i=0; i<loopCount; i++) {
            int indexOfLast = snakeList.size() - 1;
            int newX = snakeList.get(indexOfLast).getX() + 25;
            int newY = snakeList.get(indexOfLast).getY() + 25;
            Snake newSnake = new Snake(newX, newY);
            snakeList.add(newSnake);
            move();
        }
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(new Color(55, 64, 24));
        for (Snake snake : snakeList) {
            snake.paintComponent(g);
        }
    }
    
    public boolean hitSide() {
        if (snakeList.get(0).getX() < -2) {
            return true;
        } else if (snakeList.get(0).getX() + 10 > game.getWidth() + 2) {
            return true;
        } else if (snakeList.get(0).getY() < -2) {
            return true;
        } else if (snakeList.get(0).getY() + 10 > game.getHeight() - 18) {
            return true;
        }
        
        return false;
    }
    
    public void move() {
        int placeHolderX, placeHolderY;
        int newX, newY;
        
        placeHolderX = snakeList.get(0).getX();
        placeHolderY = snakeList.get(0).getY();
        snakeList.get(0).setX(placeHolderX + (int)deltaX);
        snakeList.get(0).setY(placeHolderY + (int)deltaY);
        for (int i = 1; i < snakeList.size(); i++) {
            newX = placeHolderX;
            newY = placeHolderY;
            placeHolderX = snakeList.get(i).getX();
            placeHolderY = snakeList.get(i).getY();
            snakeList.get(i).setX(newX);
            snakeList.get(i).setY(newY);
        }
    }
    
    public void increaseSpeed() {
        moveSpeed = moveSpeed + 0.015;
    }
    
    public void keyPressed(int keyCode) {
        deltaX = 0;
        deltaY = 0;
        
        switch(keyCode) {
            case KeyEvent.VK_UP: deltaY = -moveSpeed;
                                 direction = "up";
                                 break;
            case KeyEvent.VK_DOWN: deltaY = moveSpeed;
                                   direction = "down";
                                   break;
            case KeyEvent.VK_LEFT: deltaX = -moveSpeed;
                                   direction = "left";
                                   break;
            case KeyEvent.VK_RIGHT: deltaX = moveSpeed;
                                    direction = "right";
                                    break;
        }
    }
    
    /*
        Checks to see if the snake eats itself depending on the direction it is moving
    */
    public boolean checkSnakeCollision() {
        if (snakeList.size() > 10) {
            if (direction.equals("up")) {
                // Gets top of snake coordinates and makes line
                int frontX = snakeList.get(0).getX();
                int frontY = snakeList.get(0).getY();
                Line2D topSnake = new Line2D.Double(frontX, frontY, frontX + 10, frontY);

                return checkLines(topSnake);
                
            } else if (direction.equals("down")) {
                // Gets bottom of snake coordinates and makes line
                int frontX = snakeList.get(0).getX();
                int frontY = snakeList.get(0).getY() + 10;
                Line2D topSnake = new Line2D.Double(frontX, frontY, frontX + 10, frontY);

                return checkLines(topSnake);
                
            } else if (direction.equals("left")) {
                // Gets left of snake coordinates and makes line
                int frontX = snakeList.get(0).getX();
                int frontY = snakeList.get(0).getY();
                Line2D topSnake = new Line2D.Double(frontX, frontY, frontX, frontY + 10);

                return checkLines(topSnake);
                
            } else if (direction.equals("right")) {
                // Gets right of snake coordinates and makes line
                int frontX = snakeList.get(0).getX() + 10;
                int frontY = snakeList.get(0).getY();
                Line2D topSnake = new Line2D.Double(frontX, frontY, frontX, frontY + 10);

                return checkLines(topSnake);
            }
        }
        return false;
    }
    
    public boolean checkLines(Line2D topSnake) {
        for (int i = 10; i < snakeList.size(); i++) {
            if (topSnake.intersects(snakeList.get(i).getBounds())) {
                return true;
            }
        }
        return false;
    }
    
    public double getSpeed() {
        return moveSpeed;
    }
    
    public Rectangle getFirstBounds() {
        return new Rectangle(snakeList.get(0).getBounds());
    }
}
