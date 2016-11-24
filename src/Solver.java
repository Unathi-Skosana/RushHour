import java.awt.Color;
import java.awt.Rectangle;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.RuntimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class Solver {
    private HashMap<String, String> seenStates;
    private Queue<String> statesTobeExplored;
    private HashMap<String, String> moves;
    private int numBlocks;
    private ArrayList<String> solution;
    public String boardfile;
    public RushHourBlockGrid gameBoard;

    /** 
      * initializes seenStates, statesTobeExplored, moves and gets the
      * number of blocks in gameBoard.
      * @param gameBoard - A RushHourBlockGrid object that represents
      *      the board to be solved.
      */

    public Solver(String boardfile) throws IOException {
        this.boardfile = boardfile;
        this.gameBoard = parseArgs(boardfile);
        this.numBlocks =  gameBoard.getBlockList().size();
        this.seenStates = new HashMap<String, String>();
        this.statesTobeExplored = new Queue<String>();
        this.moves = new HashMap<String, String>();
        this.solution = new ArrayList<String>();
    }

    /** 
      * processes a hash of a board, if only and only if it has been not been
      * explored before if so, it adds the current state and next state to
      * seen states map and adds the moves carried out to get to next state
      * from current state and enqueues the next state to the work queue.
      *
      * @param currentState - hash of current state of gameBoard
      * @param nextState - hash of next state of gameBoard
      * @param blockID - hash of block moved from currentState to nextState
      * @param move - direction in which block was moved from currentState to nextState
      * @param spaces - number of spaces the block moved from currentState to nextState
      */

    public void addUnexploredStates(String currentState, String nextState,
                String blockID, String move, int spaces) throws InterruptedException {
        if (!seenStates.containsKey(nextState)) {
            seenStates.put(nextState, currentState);
            moves.put(nextState, blockID + " " + spaces + " " + move);
            statesTobeExplored.enqueue(nextState);
        }
    }

    /**
      * constructs a RushHourBlockGrid object from its hash representation.
      * @param hashCode - representation of a board game
      * @return board - RushHourBlockGrid object constructed from the hash.
      */

    public RushHourBlockGrid constructBoardfromHash(String hashCode) {
        RushHourBlockGrid board =  new RushHourBlockGrid(gameBoard.getBoardWidth(),
                gameBoard.getBoardHeight());

        int numProperties = 8;
        
        for (int i = numProperties; i <= this.numBlocks * numProperties; i = i + numProperties) {
            String blockHash = hashCode.substring(i - numProperties, i);
            int x = Integer.parseInt(blockHash.substring(0, 1));
            int y  = Integer.parseInt(blockHash.substring(1, 2));
            int width  = Integer.parseInt(blockHash.substring(2, 3));
            int height = Integer.parseInt(blockHash.substring(3, 4));
            String color =  blockHash.substring(4,6);
            String allowedMoves = blockHash.substring(6, 8);
            board.addBlock(x, y, width, height, color, allowedMoves);
        }

        return board;
    }

    /**
      * checks if game is solved by checking if the parsed
      * block is red in color, if so it checks it's touching
      * the right edge of the game board.
      * @param possibleRedBlock - A RushHourBlock object that could be red.
      * @return solved - returns true if game is solved, returns a solve
      *     otherwise.
      */

    public boolean isGameSolved(RushHourBlock possibleRedBlock) {
        if (possibleRedBlock.getColor().equals("RE")) {
            return (possibleRedBlock.getX() + possibleRedBlock.getWidth() == gameBoard.getBoardWidth());
        } else {
            return false;
        }
    }

    /**  
      * Outer loop generates a temporary RushHourBlockGrid each time
      * and generates all it's possible reacheable configurations from it's state
      * middle loop iterates through every block from the current state
      * Inner loop attempts to move the current block as far as possible
      * in the directions of its orientation.
      * @return solvable - a boolean value that depicts whether the
      *     board was solved or not.
      */

    public boolean solve() throws InterruptedException, IOException {
        String initialState = gameBoard.generateBoardHash();
        addUnexploredStates(null, initialState, null, null, 0); 
        
        while (!statesTobeExplored.isEmpty())  {
            String gridState = statesTobeExplored.dequeue();
            RushHourBlockGrid currentGrid = constructBoardfromHash(gridState);
            ArrayList<RushHourBlock> blockList = currentGrid.getBlockList();
                
            for (int i = 0; i < blockList.size(); i++) {
                RushHourBlock currentBlock = blockList.get(i);
                boolean endOfNode = false;
                int spaces = 1;

                while (!endOfNode) {
                    if (currentBlock.getOrientation().equals("h")) {
                        if (currentGrid.canTraverseRightNode(currentBlock, spaces)) {
                            currentGrid.traverseRightNode(currentBlock, spaces);
                            String nextState = currentGrid.generateBoardHash();
                            String color = currentBlock.getColor();
                            addUnexploredStates(gridState, nextState, color, "R", spaces);

                            if (isGameSolved(currentBlock)) {
                                trace(solution, this.seenStates, this.moves, nextState);
                                outputSolution(solution);
                                
                                return true;

                            } else {
                                currentGrid.traverseLeftNode(currentBlock, spaces);
                            }

                        } else if (currentGrid.canTraverseLeftNode(currentBlock, spaces)) {
                            currentGrid.traverseLeftNode(currentBlock, spaces);
                            String nextState = currentGrid.generateBoardHash();
                            String color = currentBlock.getColor();
                            addUnexploredStates(gridState, nextState, color, "L", spaces);
                            currentGrid.traverseRightNode(currentBlock, spaces);
                        
                        } else {
                            endOfNode = true;
                        }
                    } else {
                        if (currentGrid.canTraverseDownNode(currentBlock, spaces)) {
                            currentGrid.traverseDownNode(currentBlock, spaces);
                            String nextState = currentGrid.generateBoardHash();
                            String color = currentBlock.getColor();
                            addUnexploredStates(gridState, nextState, color, "D", spaces);
                            currentGrid.traverseUpNode(currentBlock, spaces);

                        } else if (currentGrid.canTraverseUpNode(currentBlock, spaces)) {
                            currentGrid.traverseUpNode(currentBlock, spaces);
                            String nextState = currentGrid.generateBoardHash();
                            String color = currentBlock.getColor();
                            addUnexploredStates(gridState, nextState, color, "U", spaces);
                            currentGrid.traverseDownNode(currentBlock, spaces);
                        
                        } else {
                            endOfNode = true;
                        }
                    }

                    spaces++;
                }
            }
        }

        return false;
    }

    /**
      * traces the solution using recursion by accessing the seen
      * states map that maps the nextState to the previousState
      * until the previousState is null, adding the corresponding
      * the moves into the solution ArrayList, preserving the order
      * of arrival.
      * @param solution ArrayList to hold the solution.
      * @param seenStates - mapping of seen states.
      * @param moves - moves carried out to get from previous state to next state
      * @param nextState - nextState is initiailly the solution.
      */

    private void trace(ArrayList<String> solution, HashMap<String, String> seenStates,
            HashMap<String, String> moves, String nextState) {
        String previousState = seenStates.get(nextState);
        if (previousState != null) {
            trace(solution, seenStates, moves, previousState);
            solution.add(moves.get(nextState));
        }
    }

    /** 
      * outputs the solution to a text file,
      * @param solution - contains the solution 
      *     populated by the trace method.
      */

    public void outputSolution(ArrayList<String> solution) throws IOException  {
        FileWriter writer = new FileWriter(boardfile + "_sol");
        for (String str: solution) {
            writer.write(str);
            writer.write("\n");
        }

        writer.close();
    }

    public RushHourBlockGrid parseArgs(String inputfile) throws IOException {
        validateBoard(new Scanner(new File(inputfile)));
        Scanner input = new Scanner(new File(inputfile));
        
        int numberOfLines = lineCounter(inputfile) / 4;
        RushHourBlockGrid blockGrid = new RushHourBlockGrid(6, 6);
        
        for (int i = 0; i < numberOfLines; i++) {
            String color = input.nextLine();
            int x = input.nextInt();
            input.hasNext(" ");
            int y = input.nextInt();
            input.nextLine();
            int width = input.nextInt();
            input.hasNext(" ");
            int height = input.nextInt();
            input.nextLine();
            String allowedMoves = input.nextLine();
            blockGrid.addBlock(x, y, width, height, color, allowedMoves);
        }

        return blockGrid;
    }

    private static int lineCounter(String filename) throws FileNotFoundException {
        Scanner file = new Scanner(new File(filename));
        int count = 0;
        
        while (file.hasNext()) {
            count++;
            file.nextLine();
        }

        return count;
    }

    private static void validateBoard(Scanner input) {
        if (!input.nextLine().equals("RE")) {
            throw new RuntimeException("Error: Properties of the red block should be specified foremost!");
        }

        // Skip two lines.
        input.nextLine();
        input.nextLine();
        String allowedMoves = input.nextLine();

        if (!allowedMoves.contains("LR")) {
            throw new RuntimeException("Error: The red block should be limited to only horizontal movements!");
        }
    }

}
