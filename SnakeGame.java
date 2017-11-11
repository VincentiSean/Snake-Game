package snakegame;

import javax.swing.JFrame;

/**
 *
 * @author Sean
 */
public class SnakeGame extends JFrame {
    
    private SnakePanel panel;
    
    public SnakeGame() {
        setSize(600, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Snake");
        setLocationRelativeTo(null);
        
        panel = new SnakePanel(this);
        add(panel);
        pack();
    }
    
    public static void main(String[] args) {
        new SnakeGame();
    }
    
}
