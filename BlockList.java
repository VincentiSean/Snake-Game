package snakegame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class BlockList {
    
    private ArrayList<Block> blockList;
    private SnakeList player;
    
    public BlockList(SnakeList player) {
        blockList = new ArrayList<>();
        this.player = player;
    }
    
    public void paintBlocks(Graphics g) {
        for (Block block : blockList) {
            block.paintComponent(g);
        }
    }
    
    /*
        Every level increased the number of block by 2
    */
    public void addBlocks(int level) {
        int numBlocks = level * 2;
        
        for (int i = 0; i < numBlocks; i++) {
            blockList.add(new Block());
        }
    }
    
    public void clearBlocks() {
        blockList.clear();
    }
    
    /*
        Checks for snake suicide via block/snake/side hit detection
    */
    public boolean checkSuicide() {
        if (player.checkSnakeCollision() || player.hitSide())
            return true;
        for (Block block : blockList) {
            if (block.getBounds().intersects(player.getFirstBounds()))
                return true;
        } 
        return false;
    }
    
    public boolean isEmpty() {
        return blockList.isEmpty();
    }
    
    public int size() {
        return blockList.size();
    }
    
    public Rectangle getBounds(int index) {
        return blockList.get(index).getBounds();
    }
}
