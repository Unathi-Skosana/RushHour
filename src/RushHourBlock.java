import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public class RushHourBlock {
    private final String color;
    private int width;
    private int height;
    private Position point;
    private String hashCode;
    private String allowedMoves;

    /**
      * initializes a block object by assigning
      * it's properties and instatiates
      * a list containing legal directions
      * for this block and generates a hash code
      * for this block.
      * @param point - position of block
      * @param width -  width
      * @param height - height
      * @param color - color of block
      * @param legalDirections - allowed directions of movement.
      */

    public RushHourBlock(Position point, int width,
            int height, String color, String allowedMoves) {
        this.point = point;
        this.width = width;
        this.height = height;
        this.color  = color;
        this.allowedMoves = allowedMoves;
        this.hashCode = getHashCode();
    }
    
    /**
      * returns color of block as a
      * string.
      * @return String color
      */

    public String getColor() {
        return color;
    }

    /**
      * returns legal directions list
      * @return legalMovesList ArrayList of Strings
      *     containing legal moves.
      */

    public String getAllowedMoves() {
        return allowedMoves;
    }

    /**
      * returns width.
      * @return int width
      */

    public int getWidth() {
        return width;
    }

    /**
      * returns height.
      * @return int height
      */

    public int getHeight() {
        return height;
    }

    /**
      * returns the x coordinate of this block,
      * at the instance this method was called.
      * @return int X
      */

    public int getX() {
        return point.getX();
    }

    /**
      * returns the y coordinate of this block,
      * at the instance this method was called.
      * @return int Y
      */

    public int getY() {
        return point.getY();
    }

    /**
      * assigns new x cooridnate to this block,
      * and updates the hash code of the block.
      * @param newX - the new x coordinate to be set as an int
      */

    public void setX(int newX) {
        point.setX(newX);
        hashCode = getHashCode();
    }

    /**
      * assigns new y cooridnate to this block,
      * and updates the hash code of the block.
      * @param newY - the new y coordinate to be set as an int
      */

    public void setY(int newY) {
        point.setY(newY);
        hashCode = getHashCode();
    }

    /**
      * genarates the hash code of this block
      * by concatinating all its properties together.
      * @return hash - resulting String.
      */

    public String getHashCode() {
        String hash = point.getX() + "" + point.getY() + "" + width + "" + height + "" + color;
        hash = hash + allowedMoves;

        return hash;
    }

    /**
      * returns a String representation of the
      * this block by searching the legal directions list.
      * @return String
      */

    public String getOrientation() {
        if (allowedMoves.contains("LR")) {
            return "h";
        } else {
            return "v";
        }
    }
}
