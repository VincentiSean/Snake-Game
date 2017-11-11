package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Sean
 */
public class SnakePanel extends JPanel implements ActionListener, KeyListener {
    
    private SnakeGame game;
    private SnakeList player;
    private ArrayList<Food> foodList, removeList;
    private BlockList blockList;
    private Random rand;
    private Iterator iter;
    private int points, foodCount, level, numButtonHits;
    private double playerSpeed;
    
    public SnakePanel(SnakeGame game) {
        this.game = game;
        player = new SnakeList(game);
        
        // Set default points/food/level
        points = 0;
        foodCount = 0;
        level = 0;
        numButtonHits = 0;
        
        // Used in generating random food
        rand = new Random();
        foodList = new ArrayList<>();   // Make ArrayList for easy deletion of grabbed food via iterator
        
        // Used for deleting food
        iter = foodList.iterator();
        removeList = new ArrayList<>();
        
        // Used for generating blocks for difficulty after level 0
        blockList = new BlockList(player);
        blockList.addBlocks(level);
        generateFood();
        
        Timer timer = new Timer(5, this);
        timer.start();
        setFocusable(true);
        addKeyListener(this);
    }
    
    public void update() {
        player.move();
        checkSuicide();
        checkFood();
        checkWin();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(new Color(122, 93, 61));
        player.paintComponent(g);
        generateFood();
        paintFood(g);
        writeScore(g);
        
        blockList.paintBlocks(g);
    }
    
    /*
        Checks if food grabbed equals 100; if so -> level++
            -> blocks are cleared and new ones are added
            -> snake is reset
    */
    public void checkWin() {
        if (foodCount == 100) {
            foodCount = 0;
            JOptionPane.showMessageDialog(null, "Congratulations! \nYour score: " + points, "You beat level " + (level+1) + "!", JOptionPane.INFORMATION_MESSAGE);
            blockList.clearBlocks();
            level++;
            player = new SnakeList(game);
            player.createSnake();
            blockList.addBlocks(level);
        }
    }
    
    /*
        blockList.checkSuicide() checks if blocks/JPanel sides are hit
            as well as if snake eats self via SnakeList methods
    */
    public void checkSuicide() {
       if (blockList.checkSuicide())
            gameOver();
    }
    
    public void gameOver() {
        JOptionPane.showMessageDialog(null, "Your score: " + points, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    
    /*
        Food is randomly generated within game bounds and not inside a block
    */
    public void generateFood() {
        while(foodList.isEmpty()) {
            int xCoord = rand.nextInt(590);
            int yCoord = rand.nextInt(590);

            Food newFood = new Food(xCoord, yCoord);
            if (!blockList.isEmpty()) {
                for (int i = 0; i < blockList.size(); i++) {
                    if (!blockList.getBounds(i).intersects(newFood.getBounds())) {
                        foodList.add(newFood);
                        break;
                    }     
                }
            } else {
                foodList.add(newFood);
            }
        }
    }
    
    public void paintFood(Graphics g) {
        for (Food food : foodList) {
            food.paintComponent(g);
        }
    }
    
    /*
        Checks to see if snake eats food; if so -> food increases
            -> snake length increases +10
            -> snake speed slowly increased
    */
    public void checkFood() {
        for (Food food : foodList) {
            if (player.getFirstBounds().intersects(food.getBounds())) {
                foodCount++;
                playerSpeed = player.getSpeed();
                addPoints(playerSpeed);
                player.addSnake(10);
                player.increaseSpeed();
                removeList.add(food);
                generateFood();
                numButtonHits = 0;
            }
        }    
        foodList.removeAll(removeList);
    }
    
    public  void addPoints(Double speed) {
        if (numButtonHits > 0)
            points += ((speed * 25 ) + foodCount) / numButtonHits;
        else 
            points += (speed * 25) + foodCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e.getKeyCode());
        numButtonHits++;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    /*
        This overrides the pack() in SnakeGame.java 
        so the JFrame keeps its size.
    */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

    public void writeScore(Graphics g) {
    // Create a message telling the player the game is over
        String message = "Points: " + points;

        // Create a new font instance
        Font font = new Font("Times New Roman", Font.BOLD, 12);
        FontMetrics metrics = getFontMetrics(font);

        // Set the color of the text to red, and set the font
        g.setColor(Color.BLACK);
        g.setFont(font);

        // Draw the message to the board
        g.drawString(message, 10, 15);
        
        message = "Food: " + foodCount;
        g.drawString(message, 10, 30);
    }
}