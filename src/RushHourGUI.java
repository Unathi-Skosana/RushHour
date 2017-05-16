import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RushHourGUI {
    double spacing;
    double radius;
    RushHourBlockGrid grid; // RushHourBlockGrid object representing a board
    ArrayList<RushHourBlock> blockList; // RushHourBlock objects within the grid object above
      
    /** 
      * Constructor method
      * initializes the grid parsed as it's input
      * and gets a block list from it.
      */

    public RushHourGUI(RushHourBlockGrid grid) {
        this.grid = grid;
        this.blockList = grid.getBlockList();
        this.spacing = 0.15;
        this.radius =  0.45;

    }


    /** 
      * A visiual representation of a RushHourBlockGrid object 
      * draws vehicles as connectced squares of radius 0.45
      * and uses paintBlock() to draw filled squares with the
      * color of the respective block.
      */

    public void drawVehicles() throws InterruptedException {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.WHITE);
        int numberOfBlocks = blockList.size();
       
        for (int i = 0; i < numberOfBlocks; i++) {
            for (double n = blockList.get(i).getX() + spacing; n < blockList.get(i).getX() 
                    + blockList.get(i).getWidth(); n++) {
                for (double m = grid.getBoardHeight() - blockList.get(i).getY() - blockList.get(i).getHeight() + spacing;
                            m < grid.getBoardHeight() - blockList.get(i).getY(); m++) {
                    StdDraw.setPenColor(generateColor(blockList.get(i).getColor()));
                    StdDraw.filledSquare(n + radius, m + radius, radius);
                }
            }
        }

        // animation
        StdDraw.show(0);
        Thread.sleep(150);
    }

    /**
      * animates the solution to a RushHourBlockGrid object
      * by using the solution file outputted by the solver
      * object.
      */

    public void animate(String solutionfile) throws FileNotFoundException,  InterruptedException {
        int number = 0;
        StdDraw.setCanvasSize(70 * grid.getBoardWidth(), 70 * grid.getBoardHeight());
        StdDraw.setXscale(0, grid.getBoardWidth() + spacing);
        StdDraw.setYscale(0, grid.getBoardHeight() + spacing);
        
        drawVehicles();  // draw initial board configuration

        Scanner solution = new Scanner(new File(solutionfile));

        while (solution.hasNext()) {
            String color = solution.next();
            solution.hasNext(" ");
            int spaces = solution.nextInt();
            solution.hasNext(" ");
            String direction = solution.next();
            moveBlock(grid.getBlockbyColor(color), direction, spaces);
            number = number + spaces;
        }
    }

    /**
      * animates the movement of RushHourBlock objects one move at a time
      * calling drawVehicles() after every iteraration of the for loop
      * @param block - RushHourBlock object to be moved
      * @param direction - direction to move the RushHourBlock object
      * @param spaces - number of spaces to move the RushHourBlock object in specified direction.
      */

    public void moveBlock(RushHourBlock block, String direction, int spaces) throws InterruptedException {
        if (block.getOrientation().equals("h") ) {
            switch (direction) {
                case "L":
                    for (int i = 0; i < spaces; i++) {
                        block.setX(block.getX() - 1);
                        drawVehicles();
                    }

                    break;

                case "R":
                    for (int i = 0; i < spaces; i++) {
                        block.setX(block.getX() + 1);
                        drawVehicles();
                    }

                    break;
            }

        } else if (block.getOrientation().equals("v")) {
            switch(direction) {
                case "U":
                    for (int i = 0; i < spaces; i++) {
                        block.setY(block.getY() - 1);
                        drawVehicles();
                    }

                    break;

                case "D":
                    for (int i = 0; i < spaces; i++) {
                        block.setY(block.getY() + 1);
                        drawVehicles();
                    }

                    break;
            }
        }
    }

    private static Color generateColor(String color) {
        switch (color) {
            case "RE":
                return new Color(255, 0, 0);    // Red
            case "BL":
                return new Color(0, 0, 255);    // Blue
            case "PU":
                return new Color(139, 49, 255); // Purple
            case "GR": 
                return new Color(0, 255, 0);    // Green
            case "OR":
                return new Color(255, 106, 3);  // Orange
            case "LI":
                return new Color(171, 255, 0);  // Lime
            case "PI":
                return new Color(255, 0, 155);  // Pink
            case "SO":
                return new Color(0, 225, 229);  // Soda
            case "BB":
                return new Color(0, 144, 255);  // Baby blue
            case "BR":
                return new Color(134, 47, 0);   // Brown
            case "CA":
                return new Color(84, 124, 109); // Caustic
            case "MA":
                return new Color(177, 73, 102); // Maroon
            case "RO":
                return new Color(0, 133, 94);   // Royal
            case "YE":
                return new Color(255, 254, 0);  // Yellow
            case "CR":
                return new Color(255, 46, 112); // Crimson
            case "DB": 
                return new Color(0, 65, 103);   // Dark blue
            case "DG": 
                return new Color(7, 64, 25);    // Dark greeen
            case "CY":
                return new Color(102, 252, 255); // Cyan
            default:
                return new Color(91, 81, 99);  // Grayish purple
        }
    }
}
