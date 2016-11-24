import java.awt.Color;
import java.awt.Rectangle;
import java.lang.RuntimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RushHourBlockGrid {
    private RushHourBlock[][] blockGrid; 
    private ArrayList<RushHourBlock> blocks; 
    private int boardWidth;
    private int boardHeight;


    /** 
      * constructor method, initializes the game board array with
      * the parameters passed. and calls other constructor methods
      * @param boardWidth  - dimension of board.
      * @param boardHeight - dimension of board.
      */

    public RushHourBlockGrid(int boardWidth, int boardHeight) {
        this.blockGrid = new RushHourBlock[boardHeight][boardWidth];
        this.blocks = new ArrayList<RushHourBlock>();
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
    }

    /**
      * instatiates the a block object
      * with the properties parsed as arguments
      * carries out other necessary steps.
      * @param x - x coordinate 
      * @param y - y coordinate 
      * @param width - dimension. 
      * @param height - dimension.
      * @param stringColor - color of block as a String.
      */

    public void addBlock(int x, int y, int height, int width, 
            String stringColor, String orientation) {
        Position point = new Position(x, y);
        RushHourBlock block = new RushHourBlock(point, height, width, stringColor, orientation);
        blocks.add(block);
        moveBlock(block);
    }

    /**
      * the four methods that follow, check if a block
      * can move in a certain direction in certain amount
      * of spaces.
      * @param block - RushHourBlock to be moved
      * @param spaces - number of spaces to move the block.
      */

    public boolean canTraverseUpNode(RushHourBlock block, int spaces) {
        if (block.getOrientation().equals("h") 
                || block.getY() - spaces < 0) {
            return false;
        }

        int max = 0;
        
        for (int i = 1; i <= spaces; ++i) {
            if (!isPositionEmpty(block.getX(), block.getY() - i)) {
                max = i - 1;
                break;
            }

            max = max + 1; // Keep traversing node.
        }

        if (spaces > max) {
            return false;
        }

        return true;
    }

    public boolean canTraverseDownNode(RushHourBlock block, int spaces) {
        if (block.getOrientation().equals("h") 
                || block.getY() + block.getHeight() - 1 + spaces >= this.getBoardHeight()) {
            return false;
        }

        int max = 0;
        
        for (int i = 1; i <= spaces; ++i) {
            if (!isPositionEmpty(block.getX(), block.getY() + block.getHeight() - 1 + i)) {
                max = i - 1;
                break;
            }

            max = max + 1; // Keep traversing node. 
        }

        if (spaces > max) {
            return false;
        }

        return true;
    }

    public boolean canTraverseLeftNode(RushHourBlock block, int spaces) {
        if (block.getOrientation().equals("v") 
                || block.getX() - spaces < 0) {
            return false;
        }

        int max = 0;
        
        for (int i = 1; i <= spaces; ++i) {
            if (!isPositionEmpty(block.getX() - i, block.getY())) {
                max = i - 1;
                break;
            }

            max =  max + 1; // Keep traversing node.
        }
        
        if (spaces > max) {
            return false;
        }

        return true;
    }

    public boolean canTraverseRightNode(RushHourBlock block,int spaces) {
        if (block.getOrientation().equals("v") 
                || block.getX() + block.getWidth() - 1  + spaces >= this.getBoardWidth()) {
            return false;
        }
        
        int max = 0;

        for (int i = 1; i <= spaces; ++i) {
            if (!isPositionEmpty(block.getX() + block.getWidth() - 1 + i, block.getY())) {
                max  = i - 1;
                break;
            }

            max = max + 1; // Keep traversing node.
        }

        if (spaces > max) {
            return false;
        }

        return true;
    }

    /**
      * the four methods that follow move a block,
      * a certain number of spaces in a certain direction.
      * @param block - block to be moved
      * @param spaces - number of spaces
      */

    public void traverseUpNode(RushHourBlock block, int spaces) {
        nullifyBlock(block);
        block.setY(block.getY() - spaces);
        moveBlock(block);
    }

    public void traverseDownNode(RushHourBlock block, int spaces) {
        nullifyBlock(block);
        block.setY(block.getY() + spaces);
        moveBlock(block);
    }

    public void traverseLeftNode(RushHourBlock block, int spaces) {
        nullifyBlock(block);
        block.setX(block.getX() - spaces);
        moveBlock(block);
    }

    public void traverseRightNode(RushHourBlock block, int spaces) {
        nullifyBlock(block);
        block.setX(block.getX() + spaces);
        moveBlock(block);
    }

    /** 
      * moves a block in the array that represents
      * the game board.
      * @param block - block to be moved
      */

    public void moveBlock(RushHourBlock block) {
        if (block.getOrientation().equals("v")) {
            for (int i = 0; i < block.getHeight(); i++) {
                blockGrid[block.getY() + i][block.getX()] = block;
            }
        } else {
            for (int i = 0; i < block.getWidth(); i++) {
                blockGrid[block.getY()][block.getX() + i] = block;
            }
        }
    }

    /**
      * inserts a null in the array that represents the game board,
      * as a place holder for an empty slot on the board.
      * @param block -  block to be nullified.
      */

    public void nullifyBlock(RushHourBlock block) {
        if (block.getOrientation().equals("v")) {
            for (int i = 0; i < block.getHeight(); i++) {
                blockGrid[block.getY() + i][block.getX()] = null;
            }
        } else {
            for (int i = 0; i < block.getWidth(); i++) {
                blockGrid[block.getY()][block.getX() + i] = null;
            }
        }
    }

    /**
      * checks if a slot on the board is empty,
      * checking of the corresponding position
      * in the blockGrid has a null.
      * @param x - positions of the slot
      * @param y - positions of the slot
      * @return boolean
      */

    public boolean isPositionEmpty(int x, int y) {
        return blockGrid[y][x] == null;
    }

    /**
      * return block list of this RushHourBlockGrid object
      * @return ArrayList of block objects.
      */

    public ArrayList<RushHourBlock> getBlockList() {
        return this.blocks;
    }

    /**
      * returns a block with the color specified
      * if not found returns a null value.
      * @param  color - color of block
      * @return RushHourBlock - block with the specified color.
      */

    public RushHourBlock getBlockbyColor(String color) {
        int numBlocks = blocks.size();

        for (int i = 0; i < numBlocks; i++) {
            if (blocks.get(i).getColor().equals(color)) {
                return blocks.get(i);
            }
        }

        throw new RuntimeException("Error: No block with a " + color + "  color exists on this board.");
    }

    /**
      * returns width of this board.
      * @return int boardWidth
      */

    public int getBoardWidth() {
        return boardWidth;
    }

    /**
      * returns height of this board.
      * @return int boardHeight
      */

    public int getBoardHeight() {
        return boardHeight;
    }

    /**
      * generates a hash code this RushHourBlockGrid by concatenating
      * all the hashes of the blocks in this RushHourBlockGrid object.
      * @return String boardHash.
      */

    public String generateBoardHash() {
        StringBuilder boardHash = new StringBuilder("");

        for (int i = 0; i < blocks.size(); i++) {
            boardHash.append(blocks.get(i).getHashCode());
        }

        return boardHash.toString();
    }

}
